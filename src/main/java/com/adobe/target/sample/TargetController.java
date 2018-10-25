/*
 * ADOBE CONFIDENTIAL. Copyright 2014 Adobe Systems Incorporated. All Rights
 * Reserved. NOTICE: All information contained
 * herein is, and remains the property of Adobe Systems Incorporated and its
 * suppliers, if any. The intellectual and
 * technical concepts contained herein are proprietary to Adobe Systems
 * Incorporated and its suppliers and are protected
 * by all applicable intellectual property laws, including trade secret and
 * copyright law. Dissemination of this
 * information or reproduction of this material is strictly forbidden unless
 * prior written permission is obtained
 * from Adobe Systems Incorporated.
 */
package com.adobe.target.sample;

import com.adobe.experiencecloud.target.client.MarketingCloudClient;
import com.adobe.experiencecloud.target.client.model.MarketingCloudClientOfferResponse;
import com.adobe.experiencecloud.target.client.model.MarketingCloudClientRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class TargetController {

  @Autowired
  MarketingCloudClient marketingCloudClient;
  
  @GetMapping("/getOffer_TargetOnly")
  public @ResponseBody MarketingCloudClientOfferResponse getOffer_TargetOnly(
    @RequestParam(name = "clientId", required = false, defaultValue = "adobetargetmsdbile") String clientId,
    @RequestParam(name = "organizationId", required = false, defaultValue = "2274402E5A99659A0A495EDC@AdobeOrg") String organizationId,
    @RequestParam(name = "mbox", required = false, defaultValue = "batch-req-1") String mbox,
    @RequestParam(name = "contentAsJson", required = false, defaultValue = "false") String contentAsJSon,

    Model model, HttpServletRequest request, HttpServletResponse responseObj) throws Exception {

    String result = null;    
     RequestCreationUtility util = new RequestCreationUtility();

    boolean contentAsJson = Boolean.parseBoolean( contentAsJSon );
    util.setIncludeUserId( true );
    util.setContentAsJson( contentAsJson );
    setCookies(marketingCloudClient, request, organizationId, util );

    List <String> mboxes = Arrays.asList( mbox.split( "," ) );
    MarketingCloudClientRequest clientRequest = util.createRequestWithMultipleMboxes( mboxes );

    MarketingCloudClientOfferResponse response = marketingCloudClient.getOffer( clientRequest );
    if (response != null) {
      Gson gson = new Gson();
      result = gson.toJson( response );
      String content = result;
      System.out.println( "from sdk " + response.getTargetCookie());
      Cookie tcook = new Cookie( response.getTargetCookie().getName(),
        URLEncoder.encode( response.getTargetCookie().getValue(), "UTF-8" ) );
      tcook.setMaxAge( (int) response.getTargetCookie().getMaxAge() );
      tcook.setPath( "/" );
      responseObj.addCookie( tcook );
      model.addAttribute( "content", content );
      model.addAttribute( "organizationId", organizationId );
      model.addAttribute( "content", content );
      model.addAttribute( "visitorState", response.getVisitorState() );

    }

    return response;
  }

  @GetMapping("/getOffer_MCID")
  public String getOffer_MCID(
    @RequestParam(name = "clientId", required = false, defaultValue = "miaprovaamericaspart") String clientId,
    @RequestParam(name = "organizationId", required = false, defaultValue = "2274402E5A99659A0A495EDC@AdobeOrg") String organizationId,
    @RequestParam(name = "mbox", required = false, defaultValue = "ab_serverside_test1") String mbox,
    @RequestParam(name = "contentAsJson", required = false, defaultValue = "false") String contentAsJSon,
    Model model,
    HttpServletRequest request, HttpServletResponse response) throws Exception {


    RequestCreationUtility util = new RequestCreationUtility();
    boolean contentAsJson = Boolean.parseBoolean( contentAsJSon );
    util.setContentAsJson( contentAsJson );
    setCookies( marketingCloudClient,request, organizationId, util );

    List <String> mboxes = Arrays.asList( mbox.split( "," ) );
    MarketingCloudClientRequest clientRequest = util.createRequestWithMultipleMboxes( mboxes );
    MarketingCloudClientOfferResponse offerresponse = marketingCloudClient.getOffer( clientRequest );

    if (offerresponse != null) {
      Gson gson = new Gson();
      gson.toJson( offerresponse );

      System.out.println( gson.toJson( offerresponse ) );
      System.out.println();

      Cookie tcook = new Cookie( offerresponse.getTargetCookie().getName(),
        URLEncoder.encode( offerresponse.getTargetCookie().getValue(), "UTF-8" ) );
      tcook.setMaxAge( (int) offerresponse.getTargetCookie().getMaxAge() );
      tcook.setPath( "/" );
      response.addCookie( tcook );

      model.addAttribute( "content", offerresponse );
      model.addAttribute( "organizationId", organizationId );
      model.addAttribute( "visitorState", offerresponse.getVisitorState() );

    }

    return "getOffer_MCID";
  }

  @GetMapping("/getOffer_MCIDWithCustomerIds")
  public String getOffer_MCIDWithCustomerIds(
    @RequestParam(name = "clientId", required = false, defaultValue = "miaprovaamericaspart") String clientId,
    @RequestParam(name = "organizationId", required = false, defaultValue = "2274402E5A99659A0A495EDC@AdobeOrg") String organizationId,
    @RequestParam(name = "mbox", required = false, defaultValue = "ab_serverside_test1") String mbox,
    @RequestParam(name = "contentAsJson", required = false, defaultValue = "false") String contentAsJSon,
    @RequestParam(name = "customerID", required = false, defaultValue = "67312378756723456") String customerID,
    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

    RequestCreationUtility util = new RequestCreationUtility();
    boolean contentAsJson = Boolean.parseBoolean( contentAsJSon );
    util.setContentAsJson( contentAsJson );
    setCookies( marketingCloudClient,request, organizationId, util );

    List <String> mboxes = Arrays.asList( mbox.split( "," ) );
    util.setIncludeUserId( true );
    MarketingCloudClientRequest clientRequest = util.createRequestWithMultipleMboxes( mboxes );
    MarketingCloudClientOfferResponse offerresponse = marketingCloudClient.getOffer( clientRequest );

    if (offerresponse != null) {
      Gson gson = new Gson();
      gson.toJson( offerresponse );
      Cookie tcook = new Cookie( offerresponse.getTargetCookie().getName(),
        URLEncoder.encode( offerresponse.getTargetCookie().getValue(), "UTF-8" ) );
      tcook.setMaxAge( (int) offerresponse.getTargetCookie().getMaxAge() );
      tcook.setPath( "/" );
      response.addCookie( tcook );
      model.addAttribute( "content", offerresponse );
      model.addAttribute( "organizationId", organizationId );
      model.addAttribute( "visitorState", offerresponse.getVisitorState() );

    }

    return "getOffer_MCIDWithCustomerIds";
  }

  @GetMapping("/getOffer_MCIDAnalytics")
  public String getOffer_MCIDAnalytics(
    @RequestParam(name = "clientId", required = false, defaultValue = "japacperf8") String clientId,
    @RequestParam(name = "organizationId", required = false, defaultValue = "C3E23FE452A536D60A490D45@AdobeOrg") String organizationId,
    @RequestParam(name = "mbox", required = false, defaultValue = "harsh_homePageHero") String mbox,
    @RequestParam(name = "contentAsJson", required = false, defaultValue = "false") String contentAsJSon,
    Model model,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    @SuppressWarnings("unused")
    String result = null;      


    RequestCreationUtility util = new RequestCreationUtility();
    boolean contentAsJson = Boolean.parseBoolean( contentAsJSon );
    util.setContentAsJson( contentAsJson );
    util.setIncludeOrder(true);
    util.setIncludeProduct(true);
    setCookies(marketingCloudClient,request, organizationId, util );

    List <String> mboxes = Arrays.asList( mbox.split( "," ) );
    util.setIncludeUserId( false );
    MarketingCloudClientRequest clientRequest = util.createRequestWithMultipleMboxes( mboxes );
    MarketingCloudClientOfferResponse offerresponse = marketingCloudClient.getOffer( clientRequest );
    Gson gson = new Gson();
    if (offerresponse != null) {
      result = gson.toJson( offerresponse );
      Cookie tcook = new Cookie( offerresponse.getTargetCookie().getName(),
        URLEncoder.encode( offerresponse.getTargetCookie().getValue(), "UTF-8" ) );
      tcook.setMaxAge( (int) offerresponse.getTargetCookie().getMaxAge() );
      tcook.setPath( "/" );
      response.addCookie( tcook );
      model.addAttribute( "content", offerresponse );
      model.addAttribute( "organizationId", organizationId );
      model.addAttribute( "visitorState", offerresponse.getVisitorState() );

    }

    return "getOffer_MCIDAnalytics";
  }

  @GetMapping("/getOffer_MCIDAnalyticsAtjs")
  public String getOffer_MCIDAnalyticsAtjs(
    @RequestParam(name = "clientId", required = false, defaultValue = "japacperf8") String clientId,
    @RequestParam(name = "organizationId", required = false, defaultValue = "C3E23FE452A536D60A490D45@AdobeOrg") String organizationId,
    @RequestParam(name = "contentAsJson", required = false, defaultValue = "false") String contentAsJSon,
    @RequestParam(name = "mbox", required = false, defaultValue = "harsh_homePageHero") String mbox, Model model,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {

      RequestCreationUtility util = new RequestCreationUtility();
      boolean contentAsJson = Boolean.parseBoolean( contentAsJSon );
      util.setContentAsJson( contentAsJson );

      //setCookies(marketingCloudClient, request, organizationId, util );

      List <String> mboxes = Arrays.asList( mbox.split( "," ) );
      util.setIncludeUserId( true );
      MarketingCloudClientRequest clientRequest = util.createRequestWithMultipleMboxes( mboxes );
      MarketingCloudClientOfferResponse offerresponse = marketingCloudClient.getOffer( clientRequest );

      if (offerresponse != null) {        
        Cookie tcook = new Cookie( offerresponse.getTargetCookie().getName(),
          URLEncoder.encode( offerresponse.getTargetCookie().getValue(), "UTF-8" ) );
        tcook.setMaxAge( (int) offerresponse.getTargetCookie().getMaxAge() );
        tcook.setPath( "/" );
        Cookie cookLocHint = new Cookie( offerresponse.getTargetLocationHintCookie().getName(),
          offerresponse.getTargetLocationHintCookie().getValue() );
        tcook.setMaxAge( (int) offerresponse.getTargetCookie().getMaxAge() );
        tcook.setPath( "/" );
        response.addCookie( tcook );
        response.addCookie( cookLocHint );
        model.addAttribute( "content", offerresponse );
        model.addAttribute( "organizationId", organizationId );
        model.addAttribute( "visitorState", offerresponse.getVisitorState() );

      }
    } catch (Exception e) {
      throw e;
    }
    return "getOffer_MCIDAnalyticsAtjs";
  }

  @GetMapping("/rest/v1/getOffer")
  public @ResponseBody MarketingCloudClientOfferResponse getOfferAPI (
    @RequestParam(name = "clientId", required = false, defaultValue = "japacperf8") String clientId,
    @RequestParam(name = "organizationId", required = false, defaultValue = "C3E23FE452A536D60A490D45@AdobeOrg") String organizationId,
    @RequestParam(name = "contentAsJson", required = false, defaultValue = "false") String contentAsJSon,
    @RequestParam(name = "mbox", required = false, defaultValue = "harsh_homePageHero") String mbox, Model model,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    MarketingCloudClientOfferResponse offerresponse = null;
    try {

      RequestCreationUtility util = new RequestCreationUtility();
      boolean contentAsJson = Boolean.parseBoolean( contentAsJSon );
      util.setContentAsJson( contentAsJson );
//      util.setIncludeOrder(true);
//      util.setIncludeProduct(true);

      setCookies(marketingCloudClient, request, organizationId, util );

      List <String> mboxes = Arrays.asList( mbox.split( "," ) );
      util.setIncludeUserId( true );
      MarketingCloudClientRequest clientRequest = util.createRequestWithMultipleMboxes( mboxes );
      offerresponse = marketingCloudClient.getOffer( clientRequest );

      if (offerresponse != null) {
        Cookie tcook = new Cookie( offerresponse.getTargetCookie().getName(),
          URLEncoder.encode( offerresponse.getTargetCookie().getValue(), "UTF-8" ) );
        tcook.setMaxAge( (int) offerresponse.getTargetCookie().getMaxAge() );
        tcook.setPath( "/" );
        Cookie cookLocHint = new Cookie( offerresponse.getTargetLocationHintCookie().getName(),
          offerresponse.getTargetLocationHintCookie().getValue() );
        tcook.setMaxAge( (int) offerresponse.getTargetCookie().getMaxAge() );
        tcook.setPath( "/" );

        Cookie expCloudId = new Cookie( "mcID" , offerresponse.getId().getMarketingCloudVisitorId());
        response.addCookie( tcook );
        response.addCookie( cookLocHint );
        response.addCookie(expCloudId);

      }
    } catch (Exception e) {
      throw e;
    }
    return offerresponse;
  }

  private void setCookies(MarketingCloudClient marketingCloudClient, HttpServletRequest request, String orgainzationalId, final RequestCreationUtility util) throws UnsupportedEncodingException {
//    String hintCookie = "mboxEdgeCluster";
//    String targetCookieName = "mbox";

    if (request != null) {

      Cookie targetCookie = null;
      Cookie visitorCookie = null;
      Cookie locationHintCookie = null;
      Cookie expId = null;
      List <Cookie> cookies1 = new ArrayList <Cookie>();
      if (request.getCookies() != null) {
        cookies1 = Arrays.asList( request.getCookies() );
        if (cookies1 != null && cookies1.size() > 0) {
          for (Cookie cookie : cookies1) {
           
            if (marketingCloudClient.getMboxCookieName().equals( URLDecoder.decode(cookie.getName(),"UTF-8" ))) {
              targetCookie = cookie;
            } else if (marketingCloudClient.getVisitorCookieName(orgainzationalId).equals(  URLDecoder.decode(cookie.getName(),"UTF-8" ))) {
              visitorCookie = cookie;
            } else if (marketingCloudClient.getLocationHintCookieName().equals(  URLDecoder.decode(cookie.getName(),"UTF-8" ))) {
              locationHintCookie = cookie;
            }
            else if (cookie.getName().equals("mcID")) {
              expId = cookie;
            }
          }
          if(visitorCookie!=null)
          util.setVisitorCookie( visitorCookie.getValue() );
          if(targetCookie!=null)
          util.setTargetCookie( targetCookie.getValue() );
          if(locationHintCookie!=null)
          util.setLocationHint( locationHintCookie.getValue() );
          if (expId != null) {
            util.setExpIdCookie(expId.getValue());
          }
        }
      }
    }
  }
}
