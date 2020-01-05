package lk.mad.zerohunger;

public class HotelInfo {

    String name,address,avaliablity,email,password;

    public HotelInfo(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvaliablity() {
        return avaliablity;
    }

    public void setAvaliablity(String avaliablity) {
        this.avaliablity = avaliablity;
    }

    public HotelInfo(String name, String address, String avaliablity,String email,String password) {
        this.name = name;
        this.address = address;
        this.avaliablity = avaliablity;
        this.email=email;
        this.password=password;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
