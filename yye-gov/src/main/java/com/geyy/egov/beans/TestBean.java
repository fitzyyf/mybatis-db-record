package com.geyy.egov.beans;

import com.geyy.egov.domain.Test;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-5-29 下午9:17
 * @since JDK 1.0
 */
@Named
@ConversationScoped
public class TestBean implements Serializable {
    private static final long serialVersionUID = -5153113553037966848L;
    private List<Test> testList = new ArrayList<Test>();

    private Test test = new Test();


    @Begin
    public String createLink() {
        System.out.println(this.toString());
        System.out.println(testList.size());
        System.out.println(test.toString());
        testList.add(test);
        return null;
    }

    @End
    public String delteLink(){
        System.out.println(this.toString());
        testList.clear();
        return null;
    }

    public List<Test> getTestList() {
        return testList;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
