package vip.fairy;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.JSONWriter.Feature;
import com.alibaba.fastjson2.TypeReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import vip.fairy.model.Student;

public class JsonTest {

  @Test
  void test1() {
    JSONObject info = new JSONObject();
    info.put("name", "张三");
    info.put("age", "18");
    info.put("地理", "70");
    info.put("英语", "60");
    System.out.println(info.toJSONString());
  }

  @Test
  void test2() {
    JSONObject info1 = new JSONObject();
    info1.put("name", "张三");
    info1.put("age", "18");
    JSONObject info2 = new JSONObject();
    info2.put("name", "李四");
    info2.put("age", "19");

    //把上面创建的两个json对象加入到json数组里
    JSONArray array = new JSONArray();
    array.add(info1);
    array.add(info2);
    System.out.println(array.toJSONString());
  }

  @Test
  void test3() {
    JSONArray array = new JSONArray();
    array.add("1班");
    array.add("2班");
    array.add("3班");
    System.out.println(array.toJSONString());
  }

  @Test
  void test4() {
    JSONArray array = new JSONArray();
    array.add("1班");
    array.add("2班");
    array.add("3班");
    JSONObject school = new JSONObject();
    school.put("schoolName", "第一中学");
    school.put("teacher", "刘梅");

    JSONObject info = new JSONObject();
    info.put("name", "张三");
    info.put("age", "18");
    info.put("gradle", array);
    info.put("schoolInfo", school);

    //从info中取值
    System.out.println(info.getString("name")); //张三
    System.out.println(info.getIntValue("age"));//18
    System.out.println(info.getJSONArray("gradle"));//["1班","2班","3班"]
    System.out.println(info.getJSONObject("schoolInfo"));//{"schoolName":"第一中学","teacher":"刘梅"}
  }

  @Test
  void test5() {
    JSONObject info1 = new JSONObject();
    info1.put("name", "张三");
    info1.put("age", "18");
    JSONObject info2 = new JSONObject();
    info2.put("name", "李四");
    info2.put("age", "19");

    JSONArray array = new JSONArray();
    array.add(info1);
    array.add(info2);
    //遍历获取json数组中对象的值
    for (int i = 0; i < array.size(); i++) {
      JSONObject json = array.getJSONObject(i);
      System.out.println(json.getString("name"));
      System.out.println(json.getString("age"));
    }
  }

  @Test
  void test6() {
    JSONObject info = new JSONObject();
    info.put("name", "张三");
    info.put("age", "18");
    info.put("地理", "70");
    info.put("英语", "60");

    //JSON对象转字符串
    String str = JSON.toJSONString(info);
    System.out.println(str);

    //JSON字符串转JSON对象
    JSONObject json = JSONObject.parseObject(str);
    System.out.println(json);
  }

  @Test
  void test7() {
    String str = "{\"name\":\"张三\",\"age\":\"18\",\"地理\":\"70\",\"英语\":\"60\"}";
    byte[] bytes = str.getBytes();
    JSONObject data = JSON.parseObject(bytes);
    System.out.println(data);
  }

  @Test
  void test8() {
    String text = "[\"张三\",\"李四\",\"王五\"]";
    //json字符串转json数组
    JSONArray data = JSON.parseArray(text);

    //json数组转json字符串
    String str = JSONArray.toJSONString(data);
    System.out.println(str);
  }


  @Test
  void test9() {
    Student student = new Student("张三", 18);
    //Student对象转JSON字符串
    String studentStr = JSON.toJSONString(student);
    System.out.println(studentStr);
    //JSON字符串转Student对象
    Student data = JSON.parseObject(studentStr, Student.class);
    System.out.println(data);
  }

  @Test
  void test10() {
    Student student = new Student("张三", 18);

    //Student对象转字节数组
    byte[] text = JSON.toJSONBytes(student);
    //字节数组转Student对象
    Student data = JSON.parseObject(text, Student.class);
    System.out.println(data);
  }

  @Test
  void test11() {
    String str = """
        {
             "gradle": "高一",
             "number": "2",
             "people": [
                 {
                     "name": "张三",
                     "age": "15",
                     "phone": "123456"
                 },
                 {
                     "name": "李四",
                     "age": "16",
                     "phone": "78945"
                 }
             ]
         }
        """;
    //json字符串转Map
    Map<String, Object> map = JSONObject.parseObject(str, new TypeReference<>() {
    });
    System.out.println(map.get("gradle").toString());
    System.out.println(map.get("number").toString());
    System.out.println(map.get("people").toString());
  }

  @Test
  void test12() {
    Map<String, String> map = new HashMap<>();
    map.put("测试1", null);
    map.put("测试2", "hello");

    // v1
    // String str = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue) ;

    //{"测试2":"hello","测试1":null}
    String str = JSON.toJSONString(map, JSONWriter.Feature.WriteMapNullValue);
    System.out.println(str);
  }

  @Test
  void test13() {
    String str = """
        {
            "gradle": "高一",
            "number": "2",
            "people": [
                {
                    "name": "张三",
                    "age": "15",
                    "phone": "123456"
                },
                {
                    "name": "李四",
                    "age": "16",
                    "phone": "78945"
                }
            ]
        }
        """;
    JSONObject strJson = JSONObject.parseObject(str);
    //获取people数组
    JSONArray people = strJson.getJSONArray("people");
    //json数组转List
    List<Map> list = people.toJavaList(Map.class);
    System.out.println(list);
  }

  @Test
  void test14() {
    String str = """
        [
            {
                "isSendPhone": "true",
                "id": "22258352",
                "phoneMessgge": "为避免影响您的正常使用请及时续费，若已续费请忽略此信息。",
                "readsendtime": "9",
                "countdown": "7",
                "count": "5",
                "serviceName": "流程助手",
                "startdate": "2022-02-09 00:00:00.0",
                "insertTime": "2023-02-02 07:00:38.0",
                "enddate": "2023-02-08 23:59:59.0",
                "emailMessage": "为避免影响您的正常使用请及时续费，若已续费请忽略此信息。",
                "phone": "",
                "companyname": "顾问有限责任公司",
                "serviceId": "21",
                "isSendeMail": "true",
                "email": ""
            },
            {
                "isSendPhone": "true",
                "eid": "7682130",
                "phoneMessgge": "为避免影响您的正常使用请及时续费，若已续费请忽略此信息。",
                "readsendtime": "9",
                "countdown": "15",
                "count": "50",
                "serviceName": "经理人自助服务",
                "startdate": "2022-02-17 00:00:00.0",
                "insertTime": "2023-02-02 07:00:38.0",
                "enddate": "2023-02-16 23:59:59.0",
                "emailMessage": "为避免影响您的正常使用请及时续费，若已续费请忽略此信息。",
                "phone": "",
                "companyname": "生物科技股份有限公司",
                "serviceId": "2",
                "isSendeMail": "true",
                "email": ""
            }
        ]
        """;
    str = str.trim();
    String formatStr;
    Feature mapNullValue = Feature.WriteMapNullValue;
    if (str.startsWith("[")) {
      JSONArray data = JSON.parseArray(str);
      formatStr = JSONArray.toJSONString(data, JSONWriter.Feature.PrettyFormat, mapNullValue, JSONWriter.Feature.WriteNullListAsEmpty);
    } else {
      JSONObject strJson = JSONObject.parseObject(str);
      formatStr = JSON.toJSONString(strJson, JSONWriter.Feature.PrettyFormat, mapNullValue, JSONWriter.Feature.WriteNullListAsEmpty);
    }
    System.out.println(formatStr);
  }


}
