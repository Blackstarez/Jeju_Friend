package Elements;

import java.io.Serializable;

public class login implements Serializable, Cloneable{
    // 직렬화 - 역직렬화시 필요한 값 (고정시켜 놓음)
    private static final long serialVersionUID = 1L;
    private String ID;
    private String PW;
}