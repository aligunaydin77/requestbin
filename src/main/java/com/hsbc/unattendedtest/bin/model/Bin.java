package com.hsbc.unattendedtest.bin.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Skeleton class for the Bin entity.
 * {
 *     "id": "29211f0f-cff1-4d9e-b80d-5c5acab8e5b9",
 *     "requests": [{
 *          <<Request>>
 *     }],
 * }
 *
 * Add whatever you feel is missing.
 * */
public class Bin {

    private Collection<Request> requests;

    @Id
    private String id;

    public Bin() {
        this.requests = new ArrayList<>();
    }

    public String getId(){
       return id;
    }

    public Collection<Request> getRequests() {
        return requests;
    }

    public void addRequest(Request request) {
       requests.add(request);
    }

}
