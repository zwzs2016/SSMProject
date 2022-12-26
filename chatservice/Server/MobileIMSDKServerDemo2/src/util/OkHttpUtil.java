package util;

//import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {

    private static Logger log = LoggerFactory.getLogger(OkHttpUtil.class);

//    public static final MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
//
//    public static final MediaType mediaType = MediaType.parse("application/octet-stream");

    public final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(8000, TimeUnit.MILLISECONDS)
            .readTimeout(8000, TimeUnit.MILLISECONDS)
            .build();

    /**
     * 发送post请求通过Form表单形式
     *
     * @param reqUrl 请求url
     * @param mapParam 请求参数
     *
     */
    public  int sendPostByForm(String reqUrl, Map<String,String> mapParam){
        try {
            long startTime = System.currentTimeMillis();
            //循环form表单，将表单内容添加到form builder中
            FormBody.Builder formBody = new FormBody.Builder();
            for (Map.Entry<String, String> m : mapParam.entrySet()) {
                String name = m.getKey();
                String value = m.getValue()+"";
                formBody.add(name, value);
            }
            //构建formBody(formBody.build())，将其传入Request请求中
            Request.Builder builder = new Request.Builder().url(reqUrl).post(formBody.build());
            try(Response response = okHttpClient.newCall(builder.build()).execute()){
                String body = response.body().string();
                if (!response.isSuccessful() || response.code()!=200) return response.code();
                log.info("{} response body:{}", reqUrl.substring(reqUrl.lastIndexOf("/") + 1),
                        body);
                return 0;
            }catch(Exception e){
                log.error("调用接口出错\n"+ e.getMessage());
            }finally{
                long endTime = System.currentTimeMillis();
                log.info("{} cost time:{}", reqUrl.substring(reqUrl.lastIndexOf("/") + 1),
                        (endTime - startTime));
            }
        } catch (Exception e) {
            log.error("error", e);
        }
        return 0;
    }

    /**
     * 发送post请求通过JSON参数
     *
     * @param reqUrl 请求url
     * @param param 请求参数
     *
     */
//    public static void sendPostByJson(String reqUrl, Object param){
//        try {
//            Gson gson=new Gson();
//            String paramStr = gson.toJson(param);
//
//            RequestBody requestBody = RequestBody.create(jsonType, paramStr);
//            long startTime = System.currentTimeMillis();
//            Request.Builder builder = new Request.Builder().url(reqUrl).post(requestBody);
//
//            try(Response response = okHttpClient.newCall(builder.build()).execute()){
//                String body = response.body().string();
//            }catch(Exception e){
//                log.error("调用接口出错\n"+ e.getMessage());
//            }finally{
//                long endTime = System.currentTimeMillis();
//                log.info("{} cost time:{}", reqUrl.substring(reqUrl.lastIndexOf("/") + 1),
//                        (endTime - startTime));
//            }
//        } catch (Exception e) {
//            log.error("error", e);
//        }
//    }

    /**
     * 上传文件
     *
     * @param reqUrl 请求url
     * @param file 上传的文件
     * @param fileName 文件名称
     *
     */
//    public void uploadFile(String reqUrl, File file, String fileName) {
//        try {
//            RequestBody fileBody = RequestBody.create(mediaType, file);
//            RequestBody requestBody = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart("fileName", fileName)
//                    .addFormDataPart("file", fileName, fileBody).build();
//
//            long startTime = System.currentTimeMillis();
//            Request.Builder builder = new Request.Builder().url(reqUrl).post(requestBody);
//
//            try(Response response = okHttpClient.newCall(builder.build()).execute()){
//                String body = response.body().string();
//            }catch(Exception e){
//                log.error("调用接口出错\n"+ ExceptionUtils.getMessage(e));
//            }finally{
//                long endTime = System.currentTimeMillis();
//                log.info("{} cost time:{}", reqUrl.substring(reqUrl.lastIndexOf("/") + 1),
//                        (endTime - startTime));
//            }
//        } catch (Exception e) {
//            log.error("error", e);
//        }
//    }
}
