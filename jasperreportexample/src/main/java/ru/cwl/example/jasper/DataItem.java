package ru.cwl.example.jasper;

/**
 * Created by tischenko on 24.01.2018 15:00.
 */
public class DataItem{
    String guest;
    String roomName;
    String price;

    public DataItem(String guest, String roomName, String price) {
        this.guest = guest;
        this.roomName = roomName;
        this.price = price;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
