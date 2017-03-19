package com.hsbc.unattendedtest.bin;

import com.hsbc.unattendedtest.bin.model.Bin;
import com.hsbc.unattendedtest.bin.model.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BinControllerTest {


    @Mock
    private BinRepository binRepository;

    @Mock
    private HttpServletRequest httpRequest;

    @InjectMocks
    private BinController controller;

    @Mock
    Bin mockBin;


    @Test
    public void shouldCreateTheBinAndReturnIt() throws Exception {
        when(mockBin.getId()).thenReturn("binId");
        when(binRepository.save((Bin) any())).thenReturn(mockBin);
        String binId = controller.createABin();
        assertEquals("binId", binId);
    }

    @Test
    public void shouldRecordRequests() throws Exception {
        String binId = "binId";
        when(binRepository.findOne(binId)).thenReturn(mockBin);
        controller.recordRequest(httpRequest, binId);
        verify(binRepository).save((Bin) any());
        verify(mockBin).addRequest(any());
    }

    @Test
    public void shouldInspectRequests() throws Exception {
        String binId = "binId";
        ArrayList<Request> requestListExpected = new ArrayList<>();
        Request request1 = Request.buildFromHttpRequest(httpRequest);
        requestListExpected.add(request1);
        Request request2 = Request.buildFromHttpRequest(httpRequest);
        requestListExpected.add(request2);

        when(mockBin.getRequests()).thenReturn(requestListExpected);
        when(binRepository.findOne(binId)).thenReturn(mockBin);
        Bin bin = controller.inspectRequest(binId).getBody();

        assertThat(bin.getRequests(), hasItem(request1));
        assertThat(bin.getRequests(), hasItem(request2));

    }

    @Test
    public void shouldThrowExceptionWhenRecordWithNonExistentBinId() throws IOException {
        ResponseEntity responseEntity = controller.recordRequest(httpRequest, "NonExistentBinId");
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void shouldThrowExceptionWhenInspectWithNonExistentBinId() {
        ResponseEntity<Bin> responseEntity = controller.inspectRequest("NonExistentBinId");
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }
}
