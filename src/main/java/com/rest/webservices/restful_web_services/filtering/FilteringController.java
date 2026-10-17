package com.rest.webservices.restful_web_services.filtering;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public SomeBean filtering(){
        return new SomeBean("value1","value2","value3");
    }

    @GetMapping("/filtering-list")
    public List<SomeBean> filteringList(){
        List<SomeBean> list = new ArrayList<>();
        list.add(new SomeBean("value1","value2","value3"));
        list.add(new SomeBean("value4","value5","value6"));
        list.add(new SomeBean("value7","value8","value9"));
        return list;
    }

    @GetMapping("/filtering-with-view")
    @JsonView(View.View1.class)
    public SomeBean filteringWithView(){
        return new SomeBean("value1","value2","value3");
    }

    @GetMapping("/filtering-list-with-view")
    @JsonView(View.View2.class)
    public List<SomeBean> filteringListWithView(){
        List<SomeBean> list = new ArrayList<>();
        list.add(new SomeBean("value1","value2","value3"));
        list.add(new SomeBean("value4","value5","value6"));
        list.add(new SomeBean("value7","value8","value9"));
        return list;
    }
}
