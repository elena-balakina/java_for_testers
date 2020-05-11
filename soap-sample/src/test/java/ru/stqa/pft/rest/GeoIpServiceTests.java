package ru.stqa.pft.rest;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GeoIpServiceTests {

    @Test
    public void testMyIp(){
        String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("5.44.168.134");
        assertTrue(ipLocation.contains("RU"));
    }
}
