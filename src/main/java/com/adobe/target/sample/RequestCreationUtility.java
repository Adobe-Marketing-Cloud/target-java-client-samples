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


import com.adobe.experiencecloud.target.client.impl.exception.TargetClientException;
import com.adobe.experiencecloud.target.client.model.AuthState;
import com.adobe.experiencecloud.target.client.model.EndUserIds;
import com.adobe.experiencecloud.target.client.model.MarketingCloudClientRequest;
import com.adobe.experiencecloud.target.client.model.MboxOverride;
import com.adobe.experiencecloud.target.client.model.MboxRequest;
import com.adobe.experiencecloud.target.client.model.Order;
import com.adobe.experiencecloud.target.client.model.Product;
import com.adobe.experiencecloud.target.client.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestCreationUtility {

  private boolean includeUserId;
  private boolean includeAmaParameter;
  private boolean includeProfileParameter;
  private boolean includeProduct;
  private boolean includeOrder;
  private boolean includeMboxOverride;

  private String id = "67312378756723456";

  public void setId(String id) {
    this.id = id;
  }

  private String targetCookie = null;
  private String visitorCookie = null;
  private String locationHint = null;
  private String expIdCookie = null;
  private boolean contentAsJson = false;

  public void setExpIdCookie(String expId) {
    this.expIdCookie = expId;
  }
  
  public void setTargetCookie(String targetCookie) {
    this.targetCookie = targetCookie;
  }

  public void setVisitorCookie(String visitorCookie) {
    this.visitorCookie = visitorCookie;
  }

  public void setLocationHint(String locationHint) {
    this.locationHint = locationHint;
  }
  public boolean isContentAsJson() {
    return contentAsJson;
  }

  public void setContentAsJson(boolean contentAsJson) {
    this.contentAsJson = contentAsJson;
  }

  /**
   * @return the includeMboxOverride
   */
  public boolean isIncludeMboxOverride() {
    return includeMboxOverride;
  }

  /**
   * @param includeMboxOverride the includeMboxOverride to set
   */
  public void setIncludeMboxOverride(boolean includeMboxOverride) {
    this.includeMboxOverride = includeMboxOverride;
  }

  public boolean isIncludeAmaParameter() {
    return includeAmaParameter;
  }

  /**
   * @param includeAmaParameter the includeAmaParameter to set
   */
  public void setIncludeAmaParameter(final boolean includeAmaParameter) {
    this.includeAmaParameter = includeAmaParameter;
  }

  public boolean isIncludeProfileParameter() {
    return includeProfileParameter;
  }

  /**
   * @param includeProfileParameter the includeProfileParameter to set
   */
  public void setIncludeProfileParameter(final boolean includeProfileParameter) {
    this.includeProfileParameter = includeProfileParameter;
  }

  public boolean isIncludeProduct() {
    return includeProduct;
  }

  /**
   * @param includeProduct the includeProduct to set
   */
  public void setIncludeProduct(final boolean includeProduct) {
    this.includeProduct = includeProduct;
  }

  public boolean isIncludeOrder() {
    return includeOrder;
  }

  /**
   * @param includeOrder the includeOrder to set
   */
  public void setIncludeOrder(final boolean includeOrder) {
    this.includeOrder = includeOrder;
  }

  public String getId() {
    return id;
  }

  public String getTargetCookie() {
    return targetCookie;
  }

  public String getVisitorCookie() {
    return visitorCookie;
  }

  public String getLocationHint() {
    return locationHint;
  }

  /**
   * @return the includeUserId
   */
  public boolean isIncludeUserId() {
    return includeUserId;
  }

  /**
   * @param includeUserId the includeUserId to set
   */
  public void setIncludeUserId(final boolean includeUserId) {
    this.includeUserId = includeUserId;
  }

  public MarketingCloudClientRequest createRequestWithMultipleMboxes(List<String> mboxes)
    throws TargetClientException {

    EndUserIds customerIDs = null;

    if (includeUserId) {

      customerIDs = getCustomer();

    }
    Map<String, String> param = null;

    if (includeProfileParameter) {
      param = getProfileParam();
    }
    MboxOverride override = null;
    if (includeMboxOverride) {
      override = getMboxOverRide();
    }



    final MarketingCloudClientRequest request = MarketingCloudClientRequest.builder().id(customerIDs)
      .mboxes(createPayloadList(mboxes)).contentAsJson(contentAsJson).visitorCookie(visitorCookie)
      .targetLocationHintCookie(locationHint).targetCookie(targetCookie)
      .profileParameters(param).mboxOverride(override)
      .build();

    return request;
  }

  private MboxOverride getMboxOverRide() {
    MboxOverride mboxOverride = MboxOverride.builder().aamResponse("1234").aamSegments("1234")
      .ip("10.1.1.1").time(System.currentTimeMillis()).visitorPercentage(10).build();
    return mboxOverride;
  }

  public MarketingCloudClientRequest createRequestWithSingleMbox() throws TargetClientException {

    EndUserIds customerIDs = null;
    if (includeUserId) {
      customerIDs = getCustomer();
    }
    Map<String, String> param = null;

    if (includeProfileParameter) {
      param = getProfileParam();
    }

    final MarketingCloudClientRequest request = MarketingCloudClientRequest.builder().id(customerIDs)
      .mbox(createPayload()).visitorCookie(visitorCookie).targetCookie(targetCookie)
      .contentAsJson(true).targetLocationHintCookie(locationHint).profileParameters(param).build();

    return request;
  }

  private MboxRequest createPayload() throws TargetClientException {
    Product pro = null;
    Order o = null;

    final List<String> purchasedProductIds = new ArrayList<String>();
    purchasedProductIds.add("7632");
    purchasedProductIds.add("12253");
    purchasedProductIds.add("4949");
    if (includeProduct) {
      pro = new Product();
      pro.setId("5431153");
      pro.setCategoryId("9328114230");
    }

    if (includeOrder) {
      o = new Order();
      o.setId("34366");
      o.setPurchasedProductIds(purchasedProductIds);
      o.setTotal(1425.34);
    }

    return MboxRequest.builder().indexId(0).mbox("ab_serverside_test1").order(o).product(pro).build();
  }

  private List<MboxRequest> createPayloadList(List<String> mboxes) throws TargetClientException {
    final List<MboxRequest> result = new ArrayList<MboxRequest>();
    Product pro = null;
    Order o = null;

    final List<String> purchasedProductIds = new ArrayList<String>();
    purchasedProductIds.add("7632");
    purchasedProductIds.add("12253");
    purchasedProductIds.add("4949");

    if (includeProduct) {
      pro = new Product();
      pro.setId("5431153");
      pro.setCategoryId("9328114230");
    }

    if (includeOrder) {
      o = new Order();
      o.setId("34366");
      o.setPurchasedProductIds(purchasedProductIds);
      o.setTotal(1425.34);
    }
    int i=0;
    for (String mbox : mboxes) {
      result.add(MboxRequest.builder().mbox(mbox).order(o).indexId(i).product(pro).build());
      i++;
    }

    return result;
  }
  
  private EndUserIds getCustomer() {
    EndUserIds customerIDs = null;
    final User user = User.builder().id(id).authenticatedState(AuthState.AUTHENTICATED)
      .integrationCode("userid").build();
    final User user1 = User.builder().id("550e8400-e29b-41d4-a716-446655440000").authenticatedState(AuthState.LOGGED_OUT)
          .integrationCode("puuid").build();
    final List<User> users = new ArrayList<User>();
    users.add(user);
    users.add(user1);

    if(targetCookie!=null) {
      customerIDs = EndUserIds.builder().customerIds(users).marketingCloudVisitorId(expIdCookie).build();
    }else {
//      String tntId = UUID.randomUUID().toString().replaceAll("-", "");
      customerIDs = EndUserIds.builder().marketingCloudVisitorId(expIdCookie).customerIds(users).build();
    }


    return customerIDs;

  }

  private Map<String, String> getProfileParam() {
    final Map<String, String> result = new HashMap<String, String>();
    result.put("age", "25");
    return result;
  }

}
