package org.example.Tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;

public class MyTag extends TagSupport {
    private String lang;
    private String price;
    @Override
    public int doStartTag() throws JspException {


        JspWriter out = pageContext.getOut();
        if(lang.equals("eu")||(lang.equals(""))){
            try {
                out.print(price+".00$");
            } catch (IOException e) {
                throw new JspException(e);
            }
        }else{
            try {
                out.print(price+",00$");
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
