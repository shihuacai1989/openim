namespace java com.openim.common.im.bean;

struct ThriftDeviceMsg {
    1: required i32 type
    2: optional string to
    3: optional string msg
    4: optional string from
    5: optional string loginId
    6: optional string pwd
    7: optional string serverQueue
}

