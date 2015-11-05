[["java:package:com.hp.tel.ice"]]
module book {
    struct Message{
        string name;
        int type;
        bool valid;
        double price;
        string content;
    };
    interface OnlineBook{
        Message bookTick(Message msg);
    };
};