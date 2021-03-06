/*
 * Copyright (C) 2017 Black Duck Software Inc.
 * http://www.blackducksoftware.com/
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Black Duck Software ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Black Duck Software.
 */
package com.synopsys.integration.alert.channel.hipchat;

import com.synopsys.integration.alert.channel.ChannelTest;
import com.synopsys.integration.alert.common.model.AggregateMessageContent;
import com.synopsys.integration.alert.common.model.LinkableItem;

public class HipChatChannelTest extends ChannelTest {

    //FIXME fix Tests
    //    @Test
    //    @Tag(TestTags.DEFAULT_INTEGRATION)
    //    @Tag(TestTags.CUSTOM_EXTERNAL_CONNECTION)
    //    public void sendMessageTestIT() throws IOException, IntegrationException {
    //        final AuditUtility auditUtility = Mockito.mock(AuditUtility.class);
    //        final TestBlackDuckProperties globalProperties = new TestBlackDuckProperties(new Gson(), mockedGlobalRepository, new TestAlertProperties(), properties.getProperty(TestPropertyKey.TEST_BLACKDUCK_PROVIDER_TIMEOUT, properties.getProperty(TestPropertyKey.TEST_BLACKDUCK_PROVIDER_TRUST_HTTPS_CERT));
    //        final TestAlertProperties testAlertProperties = new TestAlertProperties();
    //        final ChannelRestConnectionFactory channelRestConnectionFactory = new ChannelRestConnectionFactory(testAlertProperties);
    //        HipChatChannel hipChatChannel = new HipChatChannel(gson, testAlertProperties, globalProperties, auditUtility, null, channelRestConnectionFactory);
    //
    //        final AggregateMessageContent messageContent = createMessageContent(getClass().getSimpleName());
    //        final int roomId = Integer.parseInt(properties.getProperty(TestPropertyKey.TEST_HIPCHAT_ROOM_ID));
    //        final boolean notify = false;
    //        final String color = "random";
    //        final HipChatChannelEvent event = new HipChatChannelEvent(RestConstants.formatDate(new Date()), "provider", "FORMAT", messageContent, null, roomId, notify, color);
    //
    //        hipChatChannel = Mockito.spy(hipChatChannel);
    //        Mockito.doReturn(new HipChatGlobalConfigEntity(properties.getProperty(TestPropertyKey.TEST_HIPCHAT_API_KEY), "")).when(hipChatChannel).getGlobalConfigEntity();
    //
    //        hipChatChannel.sendAuditedMessage(event);
    //
    //        final boolean responseLine = outputLogger.isLineContainingText("Successfully sent a " + HipChatChannel.COMPONENT_NAME + " message!");
    //
    //        assertTrue(responseLine);
    //    }
    //
    //    @Test
    //    public void createRequestThrowsExceptionWhenRoomIdIsNullTest() {
    //        final HipChatChannel hipChatChannel = new HipChatChannel(gson, null, null, null, null, null);
    //
    //        IntegrationException intException = null;
    //        try {
    //            final HipChatGlobalConfigEntity hipChatGlobalConfigEntity = new HipChatGlobalConfigEntity("key", null);
    //            final HipChatChannelEvent event = new HipChatChannelEvent(null, null, null, null, null, null, null, null);
    //            hipChatChannel.createRequests(hipChatGlobalConfigEntity, event);
    //        } catch (final IntegrationException e) {
    //            intException = e;
    //        }
    //        assertNotNull(intException);
    //    }
    //
    //    @Test
    //    public void createRequestThrowsExceptionTest() throws Exception {
    //        final AuditUtility auditUtility = Mockito.mock(AuditUtility.class);
    //        final HipChatChannel hipChatChannel = new HipChatChannel(gson, null, null, auditUtility, null, null);
    //
    //        final LinkableItem subTopic = new LinkableItem("subTopic", "Alert has sent this test message", null);
    //        final AggregateMessageContent content = new AggregateMessageContent("testTopic", "", null, subTopic, Collections.emptyList());
    //
    //        final HipChatChannelEvent event = new HipChatChannelEvent(RestConstants.formatDate(new Date()), "provider", "FORMAT",
    //            content, null, 12345, Boolean.FALSE, null);
    //
    //        final String userDir = System.getProperties().getProperty("user.dir");
    //        try {
    //            System.getProperties().setProperty("user.dir", "garbage");
    //            RuntimeException thrownException = null;
    //            try {
    //                hipChatChannel.createRequests(hipChatMockUtil.createGlobalEntity(), event);
    //            } catch (final RuntimeException e) {
    //                thrownException = e;
    //            }
    //            assertNotNull(thrownException);
    //        } finally {
    //            System.getProperties().setProperty("user.dir", userDir);
    //        }
    //    }
    //
    //    @Test
    //    public void testGlobalConfigNullTest() throws Exception {
    //        final ChannelRestConnectionFactory restFactory = Mockito.mock(ChannelRestConnectionFactory.class);
    //        final HipChatChannel hipChatChannel = new HipChatChannel(gson, null, null, null, null, restFactory);
    //
    //        Mockito.when(restFactory.createRestConnection()).thenReturn(null);
    //
    //        try {
    //            hipChatChannel.testGlobalConfig(new TestConfigModel(null));
    //            fail("Expected exception, but none was thrown");
    //        } catch (final AlertException nullEntityException) {
    //            assertEquals("The provided config was null.", nullEntityException.getMessage());
    //        }
    //
    //        hipChatMockUtil.setApiKey("apiKey");
    //        final Config config = hipChatMockUtil.createGlobalConfig();
    //
    //        try {
    //            hipChatChannel.testGlobalConfig(new TestConfigModel(config, properties.getProperty(TestPropertyKey.TEST_HIPCHAT_ROOM_ID)));
    //            fail("Expected exception, but none was thrown");
    //        } catch (final AlertException restConnectionNullException) {
    //            assertEquals("Connection error: see logs for more information.", restConnectionNullException.getMessage());
    //        }
    //    }
    //
    //    @Test
    //    public void testGlobalConfigAPIKeyNullTest() {
    //        final ChannelRestConnectionFactory restFactory = Mockito.mock(ChannelRestConnectionFactory.class);
    //        final HipChatChannel hipChatChannel = new HipChatChannel(gson, null, null, null, null, restFactory);
    //
    //        Mockito.when(restFactory.createRestConnection()).thenReturn(null);
    //
    //        try {
    //            final Config config = hipChatMockUtil.createEmptyGlobalConfig();
    //            hipChatChannel.testGlobalConfig(new TestConfigModel(config));
    //            fail("Expected exception to be thrown");
    //        } catch (final IntegrationException ex) {
    //            assertEquals("Invalid API key: API key not provided", ex.getMessage());
    //        }
    //    }
    //
    //    @Test
    //    public void testGlobalConfigValidApiKeyTest() throws Exception {
    //        final TestAlertProperties testAlertProperties = new TestAlertProperties();
    //        testAlertProperties.setAlertTrustCertificate(true);
    //
    //        final ChannelRestConnectionFactory restFactory = new ChannelRestConnectionFactory(testAlertProperties);
    //        final HipChatChannel hipChatChannel = new HipChatChannel(gson, null, null, null, null, restFactory);
    //
    //        hipChatMockUtil.setApiKey(properties.getProperty(TestPropertyKey.TEST_HIPCHAT_API_KEY));
    //        hipChatMockUtil.setHostServer("");
    //        final Config config = hipChatMockUtil.createGlobalConfig();
    //        final String validMessage = hipChatChannel.testGlobalConfig(new TestConfigModel(config, properties.getProperty(TestPropertyKey.TEST_HIPCHAT_ROOM_ID)));
    //        assertEquals("API key is valid.", validMessage);
    //    }
    //
    //    @Test
    //    public void testGlobalConfigInvalidApiKeyTest() {
    //        final TestAlertProperties testAlertProperties = new TestAlertProperties();
    //        testAlertProperties.setAlertTrustCertificate(true);
    //
    //        final ChannelRestConnectionFactory restFactory = Mockito.mock(ChannelRestConnectionFactory.class);
    //        final HipChatChannel hipChatChannel = new HipChatChannel(gson, null, null, null, null, restFactory);
    //
    //        try {
    //            final RestConnection restConnection = new RestConnection(new PrintStreamIntLogger(System.out, LogLevel.INFO), 100, true, ProxyInfo.NO_PROXY_INFO);
    //            Mockito.when(restFactory.createRestConnection()).thenReturn(restConnection);
    //
    //            hipChatMockUtil.setApiKey("garbage");
    //            try {
    //                final Config config = hipChatMockUtil.createGlobalConfig();
    //                hipChatChannel.testGlobalConfig(new TestConfigModel(config, properties.getProperty(TestPropertyKey.TEST_HIPCHAT_ROOM_ID)));
    //            } catch (final IntegrationException ex) {
    //                assertTrue(ex.getMessage().contains("Invalid API key: "));
    //            }
    //        } catch (final Exception e) {
    //            Assert.fail();
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    @Test
    //    public void testEmptyContent() throws Exception {
    //        final AuditUtility auditUtility = Mockito.mock(AuditUtility.class);
    //        final TestAlertProperties alertProperties = new TestAlertProperties();
    //        final GlobalBlackDuckRepository mockedGlobalRepository = Mockito.mock(GlobalBlackDuckRepository.class);
    //        final TestBlackDuckProperties globalProperties = new TestBlackDuckProperties(new Gson(), mockedGlobalRepository, alertProperties, properties.getProperty(TestPropertyKey.TEST_BLACKDUCK_PROVIDER_TIMEOUT, properties.getProperty(TestPropertyKey.TEST_BLACKDUCK_PROVIDER_TRUST_HTTPS_CERT));
    //        HipChatChannel hipChatChannel = new HipChatChannel(gson, alertProperties, globalProperties, auditUtility, null, null);
    //
    //        final LinkableItem subTopic = new LinkableItem("subTopic", "Alert has sent this test message", null);
    //        final AggregateMessageContent content = new AggregateMessageContent("testTopic", "", null, subTopic, Collections.emptyList());
    //
    //        final int roomId = Integer.parseInt(properties.getProperty(TestPropertyKey.TEST_HIPCHAT_ROOM_ID));
    //        final boolean notify = false;
    //        final String color = "random";
    //        final HipChatChannelEvent event = new HipChatChannelEvent(RestConstants.formatDate(new Date()), "provider", "FORMAT",
    //            content, null, roomId, notify, color);
    //
    //        hipChatChannel = Mockito.spy(hipChatChannel);
    //        Mockito.doReturn(new HipChatGlobalConfigEntity(properties.getProperty(TestPropertyKey.TEST_HIPCHAT_API_KEY), "")).when(hipChatChannel).getGlobalConfigEntity();
    //
    //        final List<Request> requestList = hipChatChannel.createRequests(hipChatChannel.getGlobalConfigEntity(), event);
    //        assertTrue(requestList.size() == 1);
    //    }
    //
    //    @Test
    //    public void testChunkedRequestList() throws Exception {
    //        final AuditUtility auditUtility = Mockito.mock(AuditUtility.class);
    //        final TestAlertProperties alertProperties = new TestAlertProperties();
    //        final GlobalBlackDuckRepository mockedGlobalRepository = Mockito.mock(GlobalBlackDuckRepository.class);
    //        final TestBlackDuckProperties globalProperties = new TestBlackDuckProperties(new Gson(), mockedGlobalRepository, alertProperties, properties.getProperty(TestPropertyKey.TEST_BLACKDUCK_PROVIDER_TIMEOUT, properties.getProperty(TestPropertyKey.TEST_BLACKDUCK_PROVIDER_TRUST_HTTPS_CERT));
    //        HipChatChannel hipChatChannel = new HipChatChannel(gson, alertProperties, globalProperties, auditUtility, null, null);
    //
    //        final AggregateMessageContent messageContent = createLargeMessageContent();
    //        final int roomId = Integer.parseInt(properties.getProperty(TestPropertyKey.TEST_HIPCHAT_ROOM_ID));
    //        final boolean notify = false;
    //        final String color = "random";
    //        final HipChatChannelEvent event = new HipChatChannelEvent(RestConstants.formatDate(new Date()), "provider", "FORMAT", messageContent, null, roomId, notify, color);
    //
    //        hipChatChannel = Mockito.spy(hipChatChannel);
    //        Mockito.doReturn(new HipChatGlobalConfigEntity(properties.getProperty(TestPropertyKey.TEST_HIPCHAT_API_KEY), "")).when(hipChatChannel).getGlobalConfigEntity();
    //
    //        final List<Request> requestList = hipChatChannel.createRequests(hipChatChannel.getGlobalConfigEntity(), event);
    //        assertTrue(requestList.size() >= 3);
    //    }

    private AggregateMessageContent createLargeMessageContent() {
        final AggregateMessageContent messageContent = createMessageContent(getClass().getSimpleName() + ": Chunked Request");
        int count = 0;
        while (gson.toJson(messageContent).length() < HipChatChannel.MESSAGE_SIZE_LIMIT * 2) {
            final LinkableItem newItem = new LinkableItem("Name " + count++, "Relatively long value #" + count + " with some trailing text for good measure...", "https://google.com");
            messageContent.getCategoryItemList().get(0).getItems().add(newItem);
        }
        return messageContent;
    }
}
