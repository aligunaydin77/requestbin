package com.hsbc.unattendedtest.bin;

import com.hsbc.unattendedtest.bin.model.Bin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RequestBinIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BinRepository binRepository;

    @Test
    public void shouldCreateABin() throws Exception {
        String binId = createAndGetBinId();
        assertThat(binRepository.findOne(binId), is(notNullValue()));

    }

    private String createAndGetBinId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/bin")).
                andExpect(status().isOk()).
                andReturn();

        return mvcResult.getResponse().getContentAsString();
    }

    @Test
    public void shouldRecordAnyGetRequest() throws Exception {
        String binId = createAndGetBinId();

        Bin bin = binRepository.findOne(binId);
        assertThat(bin.getRequests().size(), is(0));

        mockMvc.perform(get(getRecordPath(binId))).
                andExpect(status().isOk());

        bin = binRepository.findOne(binId);
        assertThat(bin.getRequests().size(), is(1));
    }

    @Test
    public void shouldInspectRequests() throws Exception {
        String binId = createAndGetBinId();

        String getContent = "content within the get request" + System.getProperty("line.separator");
        mockMvc.perform(get(getRecordPath(binId)).content(getContent)).
                andExpect(status().isOk());

        String putContent = "content within the put request" + System.getProperty("line.separator");
        mockMvc.perform(put(getRecordPath(binId)).content(putContent)).
                andExpect(status().isOk());

        String postContent = "content within the post request" + System.getProperty("line.separator");
        mockMvc.perform(post(getRecordPath(binId)).content(postContent)).
                andExpect(status().isOk());

        mockMvc.perform(get(getInspectPath(binId))).
                andExpect(status().isOk()).
                andExpect((jsonPath("$.requests[*].content", containsInAnyOrder(getContent, putContent, postContent))));
    }

    @Test
    public void shouldErrorCorrectlyWhenIdNotProvided() throws Exception {
        mockMvc.perform(put(getRecordPath(null))).andExpect(status().isNotFound());
    }

    @Test
    public void shouldErrorCorrectlyWhenInspectWithoutRecording() throws Exception {
        mockMvc.perform(get(getInspectPath("aBinId"))).andExpect(status().isNotFound());
    }

    private String getRecordPath(String binId) {
        return "/bin/" +binId;
    }

    private String getInspectPath(String binId) {
        return getRecordPath(binId) + "/inspect";
    }
}
