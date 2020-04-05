package br.com.eacf.app.entity;

import java.util.List;

public class ProducerInterval {

    private List<ProducerWin> min;

    private List<ProducerWin> max;

    public List<ProducerWin> getMin() {
        return min;
    }

    public void setMin(List<ProducerWin> min) {
        this.min = min;
    }

    public List<ProducerWin> getMax() {
        return max;
    }

    public void setMax(List<ProducerWin> max) {
        this.max = max;
    }
}