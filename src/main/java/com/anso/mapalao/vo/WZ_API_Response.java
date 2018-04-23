package com.anso.mapalao.vo;

/**请求武仲接口返回的信息基类
 * 调用接口返回信息时，需继承本类。
 * @author qiumeng
 * @Package entity
 * @Description:
 * @date 2018 2018/1/13 13:25
 */
public class WZ_API_Response {
    /**
     * 返回码，1或者200：成功，其他：失败
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        if(200==this.code){
            this.message="success";
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
