package com.hsbc.unattendedtest.bin;

import com.hsbc.unattendedtest.bin.model.Bin;
import com.hsbc.unattendedtest.bin.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * A Bin is an entity that has a URL and which stores any HTTP request that was made against it.
 *
 * Inspect ia a sub resource that allows inspection of all the requests made on this bin.
 */
@RestController
@RequestMapping("/bin")
public class BinController {

    private BinRepository binRepository;

    @Autowired
    public BinController(BinRepository binRepository) {
        this.binRepository = binRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createABin() {
        Bin bin = new Bin();
        return binRepository.save(bin).getId();
    }

    @RequestMapping(value = "/{id}", method = {GET, PUT, POST, DELETE, HEAD, PATCH, OPTIONS, TRACE})
    public ResponseEntity recordRequest(HttpServletRequest httpServletRequest, @PathVariable String id) throws IOException {
        Bin bin = binRepository.findOne(id);
        if(bin != null) {
            bin.addRequest(Request.buildFromHttpRequest(httpServletRequest));
            binRepository.save(bin);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/{id}/inspect", method = RequestMethod.GET)
    public ResponseEntity<Bin> inspectRequest(@PathVariable String id) {
        Bin bin = binRepository.findOne(id);
        if(bin == null) {
            return new ResponseEntity<Bin>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Bin>(bin, HttpStatus.OK);
        }
    }


}
