from flask import Flask, jsonify, request
from flask_cors import CORS
import requests
import bs4
from datetime import datetime, timedelta, time
import locale
import matplotlib.pyplot as plt
import pandas as pd
from pandas import read_csv
import tensorflow as tf
from sklearn.model_selection import train_test_split
import numpy as np

app = Flask(__name__)
CORS(app, support_credentials=True)

# 초기 데이터 불러옴
initial_data= read_csv('/attractionProject/weatherproject/data1.csv', sep=',')

# 나이에 따른 list 생성
userAge = {"10": [], "20": [], "30": [], "40": [], "50": [], "60": [], "70": []}
for idx, age in enumerate(initial_data['userAge']):
    userAge.setdefault(str(age), []).append(str(initial_data['attraction1'][idx]))
    userAge.setdefault(str(age), []).append(str(initial_data['attraction2'][idx]))
    userAge.setdefault(str(age), []).append(str(initial_data['attraction3'][idx]))
# 성별에 따른 list 생성
userSex = {"1": [], "2": []}
for idx,sex in enumerate(initial_data['userSex']):
    userSex.setdefault(str(sex), []).append(str(initial_data['attraction1'][idx]))
    userSex.setdefault(str(sex), []).append(str(initial_data['attraction2'][idx]))
    userSex.setdefault(str(sex), []).append(str(initial_data['attraction3'][idx]))
# 관광지에 따른 list 생성
userAttraction={}
for i in range(1,26):
    list_attraction=[]
    for idx, att in enumerate(initial_data['attraction1']):
        if i == att:
            list_attraction.append(str(initial_data['attraction2'][idx]))
            list_attraction.append(str(initial_data['attraction3'][idx]))
    for idx,att in enumerate(initial_data['attraction2']):
        if i==att:
            list_attraction.append(str(initial_data['attraction1'][idx]))
            list_attraction.append(str(initial_data['attraction3'][idx]))
    for idx,att in enumerate(initial_data['attraction3']):
        if i==att:
            list_attraction.append(str(initial_data['attraction2'][idx]))
            list_attraction.append(str(initial_data['attraction1'][idx]))
    userAttraction[str(i)]=list_attraction

# 자동화를 위해 dict에 저장
dict1={}
dict1['userAge']=userAge
dict1['userSex']=userSex
dict1['attraction']=userAttraction
print(dict1)

# value(list) 업데이트 API 생성
@app.route('/api/updateList')
def updateList():
    # Java에서 불러온 값
    select1, select2, select3 = request.args.get('select1'), request.args.get('select2'), request.args.get('select3')
    age, sex = request.args.get('userAge'), request.args.get('userSex')

    dict1.setdefault('userAge', {}).setdefault(age, []).extend([select1, select2, select3])
    dict1.setdefault('userSex', {}).setdefault(sex, []).extend([select1, select2, select3])

    return dict1

# Java에 보낼 DB 별 API 생성
@app.route('/api/attraction')
def attraction():
    # Java에서 불러온 값
    select = request.args.get('select')
    value = request.args.get('value')

    # TensorFlow를 사용하여 각 요소의 등장 횟수 계산
    unique_elements, _, counts = tf.unique_with_counts(dict1[select][value])

    # 확률 계산
    total_elements = tf.reduce_sum(counts)
    probabilities = counts / total_elements
    probabilities_val = probabilities.numpy()

    # 제일 큰 수 계산
    pro_max = tf.reduce_max(probabilities_val)

    # 결과 출력
    for element, probability in zip(unique_elements, probabilities_val):
        if pro_max == probability:
            popular = element

    # TensorFlow 값을 json으로 변환
    dict_popular={"popular":popular.numpy().decode("utf-8")}

    return jsonify(dict_popular["popular"])

# 시드 설정
tf.random.set_seed(777)
np.random.seed(7)

# 지역 코드 사전
district_code_dict = {
     '서울 암사동 유적': 111274, '잠실 관광특구': 111273, '경복궁': 111123,
     '남산공원': 111122, '창동 신경제 중심지': 111171, '홍대 관광특구': 111201,
     '강남 MICE 관광특구': 111261, '명동 관광특구': 111121,
     'DDP(동대문디자인플라자)': 111152, '어린이대공원': 111141, '여의도': 111231,
     '반포한강공원': 111262, '아차산': 111151, '서울숲공원': 111142,
    '신림역': 111241, '발산역': 111212, '북한산우이역': 111291, '서울대입구역': 111251,
    '고척돔': 111221, '구로디지털단지역': 111281, '북서울꿈의숲': 111311,
    '오목교역·목동운동장': 111301, '연신내역': 111181, '서촌': 111191, '성신여대입구역': 111161
}

# 데이터 읽고 전처리하는 함수
def read_and_preprocess_data(file_name, district_name):
    try:
        df = read_csv(file_name, encoding='UTF-8')
        df['측정일시'] = pd.to_datetime(df['측정일시'], format='%Y%m%d%H', errors='coerce')
        district_code = district_code_dict.get(district_name)
        if district_code is None:
            raise ValueError(f"잘못된 지역 이름: {district_name}")

        df_search = df[df['측정소코드'] == district_code]

        if df_search.empty:
            raise ValueError(f"{file_name}에서 해당 지역의 데이터를 찾을 수 없습니다. 건너뜁니다...")

        df_search = df_search.sort_values(by='측정일시')
        df_search['PM10'] = pd.to_numeric(df_search['PM10'], errors='coerce')
        median_value = df_search['PM10'].median()
        df_search['PM10'].fillna(median_value, inplace=True)

        data = df_search[['측정일시', 'PM10']].copy()
        return data, district_code
    except Exception as e:
        print(f"데이터를 가져오는 중 오류 발생: {e}")
        return None, None

# 결측값을 중앙값으로 채우는 함수
def fill_missing_values_with_median(df):
    return df.fillna(df.median())

# 데이터셋 생성 함수
def create_dataset(data, window_length=1):
    x_data, y_data = [], []
    for i in range(len(data) - window_length):
        x_window = data[i:i + window_length]
        y_window = data[i + window_length]

        if not np.isnan(np.sum(x_window)) and not np.isnan(y_window):
            x_data.append(x_window)
            y_data.append(y_window)

    # 마지막 데이터 처리
    last_x_window = data[-window_length:]
    last_y_window = data[-1]

    if not np.isnan(np.sum(last_x_window)) and not np.isnan(last_y_window):
        x_data.append(last_x_window)
        y_data.append(last_y_window)

    # 3D 배열로 변환
    x_data = np.reshape(x_data, (len(x_data), window_length, 1))
    y_data = np.reshape(y_data, (len(y_data), 1))

    return x_data, y_data

# 윈도우 길이 설정
window_length = 1

# 분석할 지역 이름
for arealist_key in district_code_dict.keys():
    # 파일 이름 설정
    file_names = ['dust_2013.csv','dust_2014.csv','dust_2015.csv', 'dust_2016.csv', 'dust_2017.csv', 'dust_2018.csv', 'dust_2019.csv', 'dust_2020.csv', 'dust_2021.csv','dust_2022.csv']
    total_data_points = 0
    all_data = np.array([])

    # 데이터 로드 및 전처리
    for file_name in file_names:
        data, district_code = read_and_preprocess_data(file_name, arealist_key)
        if data is not None:
            df_search = pd.read_csv(file_name, encoding='UTF-8')
            df_search = df_search[df_search['측정소코드'] == district_code]

            if not df_search.empty:
                print(f"{file_name}에서 {arealist_key}의 {len(data['PM10'])} 개의 PM10 데이터 포인트를 로드했습니다.")
                all_data = np.concatenate((all_data, data['PM10'].values))

                # 데이터에서 마지막 타임스탬프를 기반으로 예측 날짜 업데이트
                last_timestamp = df_search['측정일시'].max()  # 최대값을 직접 가져옵니다.

                if pd.notna(last_timestamp):  # last_timestamp가 NaN이 아닌지 확인
                    last_timestamp = pd.to_datetime(last_timestamp)  # pd.Timestamp로 변환
                    prediction_date_2023_str = (last_timestamp + pd.DateOffset(hours=1)).strftime('%Y%m%d%H')
                else:
                    print(f"경고: {file_name}에서 '{arealist_key}'의 데이터가 NaN입니다. 처리를 건너뜁니다...")
            else:
                print(f"경고: {file_name}에서 '{arealist_key}'의 데이터를 찾을 수 없습니다. 처리를 건너뜁니다...")

    print(f"'{arealist_key}'의 총 PM10 데이터 포인트 수: {len(all_data)}")

    # 데이터를 훈련 및 테스트 세트로 분할
    train, test = train_test_split(all_data, test_size=0.3, shuffle=False)

    df_train = pd.DataFrame({'PM10': train.flatten()})
    df_test = pd.DataFrame({'PM10': test.flatten()})

    df_train = fill_missing_values_with_median(df_train)
    df_test = fill_missing_values_with_median(df_test)

    # 훈련 데이터셋 생성
    x_train, y_train = create_dataset(df_train['PM10'].values, window_length)
    x_train = np.reshape(x_train, (x_train.shape[0], x_train.shape[1], 1))

    # 테스트 데이터셋 생성
    x_test, y_test = create_dataset(df_test['PM10'].values, window_length)
    x_test = np.reshape(x_test, (x_test.shape[0], x_test.shape[1], 1))

    # 모델 생성 및 훈련
    input_layer = tf.keras.layers.Input(shape=(window_length, 1))
    net = tf.keras.layers.LSTM(units=64, activation='relu', return_sequences=True)(input_layer)
    net = tf.keras.layers.LSTM(units=32, activation='relu')(net)
    net = tf.keras.layers.Dense(units=32, activation='relu')(net)
    net = tf.keras.layers.Dense(units=1)(net)
    model = tf.keras.models.Model(input_layer, net)
    model.compile(loss='mean_squared_error', optimizer='adam')
    model.fit(x_train, y_train, epochs=10, validation_data=(x_test, y_test))

    # 예측 및 결과 출력
    y_train_predict = model.predict(x_train)
    y_test_predict = model.predict(x_test)

    train_rmse = np.sqrt(np.mean(np.square(y_train.flatten() - y_train_predict.flatten())))
    test_rmse = np.sqrt(np.mean(np.square(y_test.flatten() - y_test_predict.flatten())))

    print(f'훈련 RMSE: {train_rmse}')
    print(f'테스트 RMSE: {test_rmse}')

    actual_data = all_data.flatten()
    plt.plot(actual_data, label='실제 PM10 농도')

    trainPredictPlot = np.full_like(all_data.flatten(), np.nan)
    trainPredictPlot[window_length:len(y_train_predict) + window_length] = y_train_predict.flatten()
    plt.plot(trainPredictPlot, label='학습데이터 예측 PM10 농도')

    # testPredictPlot의 크기를 all_data와 동일하게 만듭니다.
    testPredictPlot = np.full_like(all_data.flatten(), np.nan)
    testPredictPlot[len(all_data) - len(y_test_predict) - window_length:len(all_data) - window_length] = y_test_predict.flatten()

# API 호출 및 데이터 가져오기
def fetch_api_data(area_name):
    url = f'http://openapi.seoul.go.kr:8088/7666485574616e733131306c4a787178/xml/citydata/1/5/{area_name}'
    response = requests.get(url)
    content = response.text
    xml_obj = bs4.BeautifulSoup(content, 'lxml-xml')

    # API에서 필요한 데이터
    xml_obj_find = ['WEATHER_TIME', 'AREA_NM', 'PM10', 'PM10_INDEX']

    # 필요한 데이터만 받아와서 리스트에 추가
    found_rows = [xml_obj.find(i) for i in xml_obj_find if xml_obj.find(i)]

    # found_rows가 비어 있는지 확인
    if not found_rows:
        print(f"경고: {area_name} 지역의 데이터를 찾을 수 없습니다.")
        return None

    # 날짜 포멧 변경
    raw_date_str = found_rows[0].text.strip()
    parsed_date = datetime.strptime(raw_date_str, "%Y-%m-%d %H:%M")
    locale.setlocale(locale.LC_TIME, 'ko_KR.UTF-8')
    formatted_date = parsed_date.strftime("%Y%m%d%H00")

    # 딕셔너리로 변환
    dict_rows = {xml_obj_find[i]: found_rows[i].text for i in range(len(found_rows))}
    dict_rows['WEATHER_TIME'] = formatted_date
    print("dict_rows",dict_rows)

    return dict_rows

@app.route("/api/weather_prediction")
def weather_api():
    # java에서 값 불러옴
    value = request.args.get('value')

    data_list = []
    data_list.append(value)

    values_list = list(district_code_dict.keys())

    for i in data_list:
        select_key=values_list[int(i)-1]
        # API 데이터 호출
        api_data = fetch_api_data(select_key)

        # found_rows가 None이 아닌경우 경우 처리
        if api_data is not None:
            current_pm10_value = int(api_data['PM10'])

            # 예측할 시간을 1시간 뒤로 조정
            prediction_date_2023_str = api_data['WEATHER_TIME']
            prediction_date_2023 = datetime.strptime(prediction_date_2023_str, '%Y%m%d%H%M') + timedelta(hours=1)
            prediction_date_2023_str = prediction_date_2023.strftime('%Y%m%d%H%M')

            # 데이터셋 생성
            x_2023, y_2023 = create_dataset(np.array([current_pm10_value]), window_length)
            x_2023 = np.reshape(x_2023, (x_2023.shape[0], x_2023.shape[1], 1))

            dict_predict=[]
            # 모델로 예측
            if x_2023.shape[0] > 0:
                y_2023_predict = model.predict(x_2023)
                y_2023_predict_float = float(y_2023_predict[0][0])

                # 예측된 PM10, 현재 PM10 값
                dict_predict.extend([y_2023_predict_float, current_pm10_value])
            else:
                print("오류: 입력 데이터가 비어 있습니다.")
    return jsonify((y_2023_predict_float, current_pm10_value))


if __name__ == '__main__':
    app.run(host='0.0.0.0')

