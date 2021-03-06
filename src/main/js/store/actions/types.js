// Audit Actions
export const AUDIT_FETCHING = 'AUDIT_FETCHING';
export const AUDIT_FETCHED = 'AUDIT_FETCHED';
export const AUDIT_FETCH_ERROR = 'AUDIT_FETCH_ERROR';

// Global Config Actions
export const CONFIG_FETCHING = 'CONFIG_FETCHING';
export const CONFIG_FETCHED = 'CONFIG_FETCHED';
export const CONFIG_UPDATE_ERROR = 'CONFIG_UPDATE_ERROR';
export const CONFIG_UPDATING = 'CONFIG_UPDATING';
export const CONFIG_UPDATED = 'CONFIG_UPDATED';
export const CONFIG_TESTING = 'CONFIG_TESTING';
export const CONFIG_TEST_SUCCESS = 'CONFIG_TEST_SUCCESS';
export const CONFIG_TEST_FAILED = 'CONFIG_TEST_FAILED';

// HipChat Config Actions
export const HIPCHAT_CONFIG_FETCHING = 'HIPCHAT_CONFIG_FETCHING';
export const HIPCHAT_CONFIG_FETCHED = 'HIPCHAT_CONFIG_FETCHED';
export const HIPCHAT_CONFIG_UPDATE_ERROR = 'HIPCHAT_CONFIG_UPDATE_ERROR';
export const HIPCHAT_CONFIG_UPDATING = 'HIPCHAT_CONFIG_UPDATING';
export const HIPCHAT_CONFIG_UPDATED = 'HIPCHAT_CONFIG_UPDATED';
export const HIPCHAT_CONFIG_TESTING = 'HIPCHAT_CONFIG_TESTING';
export const HIPCHAT_CONFIG_TEST_SUCCESS = 'HIPCHAT_CONFIG_TEST_SUCCESS';
export const HIPCHAT_CONFIG_TEST_FAILED = 'HIPCHAT_CONFIG_TEST_FAILED';
export const HIPCHAT_CONFIG_SHOW_HOST_SERVER = 'HIPCHAT_CONFIG_SHOW_HOST_SERVER';
export const HIPCHAT_CONFIG_HIDE_HOST_SERVER = 'HIPCHAT_CONFIG_HIDE_HOST_SERVER';
export const HIPCHAT_CONFIG_SHOW_TEST_MODAL = 'EMAIL_CONFIG_SHOW_TEST_MODAL';
export const HIPCHAT_CONFIG_HIDE_TEST_MODAL = 'EMAIL_CONFIG_HIDE_TEST_MODAL';

// Email Config Actions
export const EMAIL_CONFIG_FETCHING = 'EMAIL_CONFIG_FETCHING';
export const EMAIL_CONFIG_FETCHED = 'EMAIL_CONFIG_FETCHED';
export const EMAIL_CONFIG_UPDATE_ERROR = 'EMAIL_CONFIG_UPDATE_ERROR';
export const EMAIL_CONFIG_UPDATING = 'EMAIL_CONFIG_UPDATING';
export const EMAIL_CONFIG_UPDATED = 'EMAIL_CONFIG_UPDATED';
export const EMAIL_CONFIG_SHOW_ADVANCED = 'EMAIL_CONFIG_SHOW_ADVANCED';
export const EMAIL_CONFIG_HIDE_ADVANCED = 'EMAIL_CONFIG_HIDE_ADVANCED';
export const EMAIL_CONFIG_SHOW_TEST_MODAL = 'EMAIL_CONFIG_SHOW_TEST_MODAL';
export const EMAIL_CONFIG_HIDE_TEST_MODAL = 'EMAIL_CONFIG_HIDE_TEST_MODAL';
export const EMAIL_CONFIG_TEST_SUCCESSFUL = 'EMAIL_CONFIG_TEST_SUCCESSFUL';

export const EMAIL_GROUPS_FETCHING = 'EMAIL_GROUPS_FETCHING';
export const EMAIL_GROUPS_FETCHED = 'EMAIL_GROUPS_FETCHED';
export const EMAIL_GROUPS_FETCH_ERROR = 'EMAIL_GROUPS_FETCH_ERROR';

// Project related Actions
export const PROJECTS_FETCHING = 'PROJECTS_FETCHING';
export const PROJECTS_FETCHED = 'PROJECTS_FETCHED';
export const PROJECTS_FETCH_ERROR = 'PROJECTS_FETCH_ERROR';

// Scheduling Config Actions
export const SCHEDULING_CONFIG_FETCHING = 'SCHEDULING_CONFIG_FETCHING';
export const SCHEDULING_CONFIG_FETCHED = 'SCHEDULING_CONFIG_FETCHED';
export const SCHEDULING_CONFIG_FETCH_ERROR = 'SCHEDULING_CONFIG_FETCH_ERROR';
export const SCHEDULING_CONFIG_UPDATE_ERROR = 'SCHEDULING_CONFIG_UPDATE_ERROR';
export const SCHEDULING_CONFIG_UPDATING = 'SCHEDULING_CONFIG_UPDATING';
export const SCHEDULING_CONFIG_UPDATED = 'SCHEDULING_CONFIG_UPDATED';
export const SCHEDULING_ACCUMULATOR_ERROR = 'SCHEDULING_ACCUMULATOR_ERROR';
export const SCHEDULING_ACCUMULATOR_RUNNING = 'SCHEDULING_ACCUMULATOR_RUNNING';
export const SCHEDULING_ACCUMULATOR_SUCCESS = 'SCHEDULING_ACCUMULATOR_SUCCESS';

// Auth related Actions
export const SESSION_INITIALIZING = 'SESSION_INITIALIZING';
export const SESSION_LOGGING_IN = 'SESSION_LOGGING_IN';
export const SESSION_LOGGED_IN = 'SESSION_LOGGED_IN';
export const SESSION_LOGGED_OUT = 'SESSION_LOGGED_OUT';
export const SESSION_LOGIN_ERROR = 'SESSION_LOGIN_ERROR';
export const SESSION_CANCEL_LOGOUT = 'SESSION_CANCEL_LOGOUT';
export const SESSION_CONFIRM_LOGOUT = 'SESSION_CONFIRM_LOGOUT';
export const SESSION_LOGOUT = 'SESSION_LOGOUT';
export const SERIALIZE = 'SERIALIZE';

// About related Actions
export const ABOUT_INFO_FETCHING = 'ABOUT_INFO_FETCHING';
export const ABOUT_INFO_FETCHED = 'ABOUT_INFO_FETCHED';
export const ABOUT_INFO_FETCH_ERROR = 'ABOUT_INFO_FETCH_ERROR';

// Refresh related Actions
export const REFRESH_ENABLE = 'REFRESH_ENABLE';
export const REFRESH_DISABLE = 'REFRESH_DISABLE';

// descriptor Actions
export const DESCRIPTORS_FETCHING = 'DESCRIPTORS_FETCHING';
export const DESCRIPTORS_FETCHED = 'DESCRIPTORS_FETCHED';
export const DESCRIPTORS_FETCH_ERROR = 'DESCRIPTORS_FETCH_ERROR';

export const DESCRIPTORS_DISTRIBUTION_FETCHING = 'DESCRIPTORS_DISTRIBUTION_FETCHING';
export const DESCRIPTORS_DISTRIBUTION_FETCHED = 'DESCRIPTORS_DISTRIBUTION_FETCHED';
export const DESCRIPTORS_DISTRIBUTION_FETCH_ERROR = 'DESCRIPTORS_DISTRIBUTION_FETCH_ERROR';
export const DESCRIPTORS_DISTRIBUTION_RESET = 'DESCRIPTORS_DISTRIBUTION_RESET';

// distribution job Actions
export const DISTRIBUTION_JOB_FETCHING = 'DISTRIBUTION_JOB_FETCHING';
export const DISTRIBUTION_JOB_FETCHED = 'DISTRIBUTION_JOB_FETCHED';
export const DISTRIBUTION_JOB_FETCH_ERROR = 'DITRIBUTION_JOB_FETCH_ERROR';
export const DISTRIBUTION_JOB_SAVING = 'DISTRIBUTION_JOB_UPDATING';
export const DISTRIBUTION_JOB_SAVED = 'DISTRIBUTION_JOB_UPDATED';
export const DISTRIBUTION_JOB_SAVE_ERROR = 'DISTRIBUTION_JOB_UPDATE_ERROR';
export const DISTRIBUTION_JOB_UPDATING = 'DISTRIBUTION_JOB_UPDATING';
export const DISTRIBUTION_JOB_UPDATED = 'DISTRIBUTION_JOB_UPDATED';
export const DISTRIBUTION_JOB_UPDATE_ERROR = 'DISTRIBUTION_JOB_UPDATE_ERROR';
export const DISTRIBUTION_JOB_TESTING = 'DISTRIBUTION_JOB_TESTING';
export const DISTRIBUTION_JOB_TEST_SUCCESS = 'DISTRIBUTION_JOB_TEST_SUCCESS';
export const DISTRIBUTION_JOB_TEST_FAILURE = 'DISTRIBUTION_JOB_TEST_FAILURE';

// system message actions

export const SYSTEM_LATEST_MESSAGES_FETCHING = 'SYSTEM_LATEST_MESSAGES_FETCHING';
export const SYSTEM_LATEST_MESSAGES_FETCHED = 'SYSTEM_LATEST_MESSAGES_FETCHED';
export const SYSTEM_LATEST_MESSAGES_FETCH_ERROR = 'SYSTEM_LATEST_MESSAGES_FETCH_ERROR';
export const SYSTEM_SETUP_FETCHING = 'SYSTEM_SETUP_FETCHING';
export const SYSTEM_SETUP_FETCH_REDIRECTED = 'SYSTEM_SETUP_FETCH_REDIRECTED';
export const SYSTEM_SETUP_FETCHED = 'SYSTEM_SETUP_FETCHED';
export const SYSTEM_SETUP_FETCH_ERROR = 'SYSTEM_SETUP_FETCH_ERROR';
export const SYSTEM_SETUP_UPDATING = 'SYSTEM_SETUP_UPDATING';
export const SYSTEM_SETUP_UPDATED = 'SYSTEM_SETUP_UPDATED';
export const SYSTEM_SETUP_UPDATE_ERROR = 'SYSTEM_SETUP_UPDATE_ERROR';

