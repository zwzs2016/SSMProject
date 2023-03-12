package com.uwan.message.net.test.voice;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


/**
 * @author admin
 */
public class EngineeCore {


    AudioFormat audioFormat;
    TargetDataLine targetDataLine;
    boolean flag = true;

    private void stopRecognize() {
        flag = false;
        targetDataLine.stop();
        targetDataLine.close();
    }

    private AudioFormat getAudioFormat() {
        // 8000,11025,16000,22050,44100
        float sampleRate = 16000;
        // 8,16
        int sampleSizeInBits = 16;
        // 1,2
        int channels = 1;
        // true,false
        boolean signed = true;
        // true,false
        boolean bigEndian = false;
        // end getAudioFormat
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }


    private void startRecognize() {
        try {
            // 获得指定的音频格式
            audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            flag = true;
            File file = new File("/home/zw/音乐/voice/voice_cache");
            if (!file.exists()) {
                file.mkdirs();
            }
            String filePath = "/home/zw/音乐/voice/voice_cache/" + System.currentTimeMillis() + ".wav";
            File audioFile = new File(filePath);

            //声音录入的权值
            int weight = 2;
            //判断是否停止的计数
            int downSum = 0;
            //判断超过20s停止播放,设置为0则不判断时间截取
            boolean startRecord = false;
            int forcedStop = 1;
            ByteArrayInputStream bais = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            AudioInputStream ais = null;
            try {
                targetDataLine.open(audioFormat);
                targetDataLine.start();
                byte[] fragment = new byte[1024];
                ais = new AudioInputStream(targetDataLine);
                Date date = new Date();
                while (flag) {
                    targetDataLine.read(fragment, 0, fragment.length);
                    //当数组末位大于weight时开始存储字节（有声音传入），一旦开始不再需要判断末位
                    if (Math.abs(fragment[fragment.length - 1]) > weight || baos.size() > 0) {
                        if (!startRecord) {
                            startRecord = true;
                            date = new Date();
                        }
                        if (forcedStop != 0) {
                            Date newDate = timePastSecond(date);
                            if (newDate.compareTo(new Date())<=0) {
                                System.out.println("录音超过20s,停止录入");
                                break;
                            }
                        }
                        baos.write(fragment);
                        System.out.println("守卫：" + fragment[0] + ",末尾：" + fragment[fragment.length - 1] + ",lenght" + fragment.length);
                        //判断语音是否停止
                        if (Math.abs(fragment[fragment.length - 1]) <= weight) {
                            downSum++;
                        } else {
                            System.out.println("重置奇数");
                            downSum = 0;
                        }
                        //计数超过20说明此段时间没有声音传入(值也可更改)
                        if (downSum > 20) {
                            System.out.println("停止录入");
                            break;
                        }
                    }
                }

                //取得录音输入流
                audioFormat = getAudioFormat();
                byte audioData[] = baos.toByteArray();
                bais = new ByteArrayInputStream(audioData);
                ais = new AudioInputStream(bais, audioFormat, audioData.length / audioFormat.getFrameSize());
                //定义最终保存的文件名
                System.out.println("开始生成语音文件");
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, audioFile);
                stopRecognize();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //关闭流
                try {
                    ais.close();
                    bais.close();
                    baos.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Date timePastSecond(Date date) {
        try {
            Calendar newTime = Calendar.getInstance();
            newTime.setTime(date);
            //日期加20秒
            newTime.add(Calendar.SECOND,20);
            Date newDate=newTime.getTime();
            return newDate;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static void main(String args[]) {

        //record 20s auto stop
        EngineeCore engineeCore = new EngineeCore();
        engineeCore.startRecognize();
    }
}

