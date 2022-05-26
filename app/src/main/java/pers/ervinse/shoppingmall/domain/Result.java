package pers.ervinse.shoppingmall.domain;


/**
 * 前后端数据协议类
 * 每次请求返回一个R对象
 */
public class Result {

    //当前请求是否正常执行
    private Boolean flag;
    //返回的查询数据
    private Object data;
    //是否抛出异常
    private Boolean errorFlag;

    //执行操作的返回对象
    public Result(Boolean flag) {
        this.flag = flag;
    }

    //查询的返回对象
    public Result(Boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    //抛出异常的返回对象
    public Result(Boolean flag, Boolean errorFlag) {
        this.flag = flag;
        this.errorFlag = errorFlag;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(Boolean errorFlag) {
        this.errorFlag = errorFlag;
    }

    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", data=" + data +
                ", errorFlag=" + errorFlag +
                '}';
    }
}
