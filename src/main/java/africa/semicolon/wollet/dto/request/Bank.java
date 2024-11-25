package africa.semicolon.wollet.dto.request;

public enum Bank {

    FCMB("214"),
    FIRST_BANK("011"),
    GTB("058");

    private String code;

    Bank(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
