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
package com.blackducksoftware.integration.alert.web.actions;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.blackducksoftware.integration.alert.Application;
import com.blackducksoftware.integration.alert.common.ContentConverter;
import com.blackducksoftware.integration.alert.common.enumeration.AuditEntryStatus;
import com.blackducksoftware.integration.alert.common.exception.AlertException;
import com.blackducksoftware.integration.alert.config.DataSourceConfig;
import com.blackducksoftware.integration.alert.database.audit.AuditEntryEntity;
import com.blackducksoftware.integration.alert.database.audit.AuditEntryRepository;
import com.blackducksoftware.integration.alert.database.audit.AuditNotificationRepository;
import com.blackducksoftware.integration.alert.database.entity.CommonDistributionConfigEntity;
import com.blackducksoftware.integration.alert.database.entity.repository.CommonDistributionRepository;
import com.blackducksoftware.integration.alert.web.model.CommonDistributionConfigRestModel;
import com.blackducksoftware.integration.alert.web.model.CommonDistributionContentConverter;
import com.blackducksoftware.integration.test.annotation.DatabaseConnectionTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

@Category(DatabaseConnectionTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:spring-test.properties")
@ContextConfiguration(classes = { Application.class, DataSourceConfig.class })
@Transactional
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class CommonDistributionConfigActionsTestIT {
    @Autowired
    private CommonDistributionRepository commonDistributionRepository;
    @Autowired
    private AuditEntryRepository auditEntryRepository;
    @Autowired
    private AuditNotificationRepository auditNotificationRepository;
    @Autowired
    private ConfiguredProjectsActions configuredProjectsActions;
    @Autowired
    private NotificationTypesActions notificationTypesActions;
    @Autowired
    private ContentConverter contentConverter;

    @Before
    public void cleanup() {
        commonDistributionRepository.deleteAll();
        auditEntryRepository.deleteAll();
        configuredProjectsActions.getConfiguredProjectsRepository().deleteAll();
        configuredProjectsActions.getDistributionProjectRepository().deleteAll();
        notificationTypesActions.getNotificationTypeRepository().deleteAll();
        notificationTypesActions.getDistributionNotificationTypeRepository().deleteAll();
    }

    @Test
    public void saveAndGetTestIT() throws AlertException {
        final String distributionType = null;
        final String name = "My Config";
        final String frequency = "DAILY";
        final String filterByProject = "true";
        final List<String> projectList = Arrays.asList("Project 1", "Project 2", "Project 3");
        final List<String> notificationTypeList = Arrays.asList("POLICY_VIOLATION", "VULNERABILITY");
        final Date lastRan = new Date(System.currentTimeMillis());
        final AuditEntryStatus status = AuditEntryStatus.SUCCESS;

        auditEntryRepository.save(new AuditEntryEntity(new Long(-1), lastRan, lastRan, status, "", ""));

        final CommonDistributionConfigRestModel commonDistributionConfigRestModel = new CommonDistributionConfigRestModel(null, null, distributionType, name, frequency, filterByProject, projectList, notificationTypeList);
        final CommonDistributionContentConverter commonDistributionContentConverter = new CommonDistributionContentConverter(contentConverter);
        final CommonDistributionConfigActions commonDistributionConfigActions = new CommonDistributionConfigActions(commonDistributionRepository, auditEntryRepository, configuredProjectsActions, notificationTypesActions,
                commonDistributionContentConverter,
                auditNotificationRepository);

        final CommonDistributionConfigEntity savedEntity = commonDistributionConfigActions.saveConfig(commonDistributionConfigRestModel);
        assertEquals(distributionType, savedEntity.getDistributionType());
        assertEquals(name, savedEntity.getName());
        assertEquals(frequency, savedEntity.getFrequency().name());
        assertEquals(filterByProject, savedEntity.getFilterByProject().toString());
        assertEquals(projectList.size(), configuredProjectsActions.getDistributionProjectRepository().count());
        assertEquals(projectList.size(), configuredProjectsActions.getConfiguredProjectsRepository().count());
        assertEquals(notificationTypeList.size(), notificationTypesActions.getDistributionNotificationTypeRepository().count());
        assertEquals(notificationTypeList.size(), notificationTypesActions.getNotificationTypeRepository().count());

        final CommonDistributionConfigRestModel updatedRestModel = (CommonDistributionConfigRestModel) commonDistributionContentConverter.populateRestModelFromDatabaseEntity(savedEntity);
        commonDistributionConfigActions.saveConfig(updatedRestModel);
        assertEquals(projectList.size(), configuredProjectsActions.getDistributionProjectRepository().count());
        assertEquals(projectList.size(), configuredProjectsActions.getConfiguredProjectsRepository().count());
        assertEquals(notificationTypeList.size(), notificationTypesActions.getDistributionNotificationTypeRepository().count());
        assertEquals(notificationTypeList.size(), notificationTypesActions.getNotificationTypeRepository().count());

        final List<CommonDistributionConfigRestModel> foundRestModels = commonDistributionConfigActions.getConfig(savedEntity.getId());
        assertEquals(1, foundRestModels.size());
        final CommonDistributionConfigRestModel foundRestModel = foundRestModels.get(0);

        assertEquals(savedEntity.getId(), contentConverter.getLongValue(foundRestModel.getId()));
        assertEquals(savedEntity.getDistributionConfigId(), contentConverter.getLongValue(foundRestModel.getDistributionConfigId()));
        assertEquals(savedEntity.getDistributionType(), foundRestModel.getDistributionType());
        assertEquals(savedEntity.getFilterByProject(), contentConverter.getBooleanValue(foundRestModel.getFilterByProject()));
        assertEquals(projectList, foundRestModel.getConfiguredProjects());
    }

}