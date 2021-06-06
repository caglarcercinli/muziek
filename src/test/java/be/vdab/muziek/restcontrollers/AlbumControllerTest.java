package be.vdab.muziek.restcontrollers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/insertArtiest.sql","/insertAlbum.sql"})
public class AlbumControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final MockMvc mvc;

    public AlbumControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }
    private long idVanTestAlbum(){
        return jdbcTemplate.queryForObject(
                "select id from albums where naam='test'",Long.class);
    }
    @Test
    void onbestaandeAlbumLezen() throws Exception {
        mvc.perform(get("/albums/{id}",-1))
                .andExpect(status().isNotFound());
    }
    @Test
    void albumLezen() throws Exception {
        mvc.perform(get("/albums/{id}",idVanTestAlbum()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("album").value("test"));
    }
}
