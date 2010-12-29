package com.opencms.engine;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-29
 * Time: 上午8:51
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("session")
public class PublishStatus implements Serializable {

    private boolean initial = false;

    private boolean end = false;

    private boolean exception = false;

    private int total ;

    private int finished;

    private int remain;

    private int percent;

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean isException() {
        return exception;
    }

    public void setException(boolean exception) {
        this.exception = exception;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public void addFinished(int finished) {
        this.finished += finished;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public double getPercent() {
        return total == 0 ? 0 : ((double)finished) / total;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void clean() {
        this.finished = 0;
        this.percent = 0;
        this.remain = 0;
        this.total = 0;
        this.initial = false;
        this.end = false;
        this.exception = false;
    }
}
