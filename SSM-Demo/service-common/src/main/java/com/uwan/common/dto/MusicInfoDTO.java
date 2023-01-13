package com.uwan.common.dto;

public class MusicInfoDTO {
//    @NotBlank(message = "标题不能为空")
//    @Length(min = 5,max = 20)
    private String title;

    private String roomId;

//    @Length(max = 100)
    private String remark;

    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
