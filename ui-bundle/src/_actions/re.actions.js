import { reConstants, userConstants } from '../_constants';
import { reService } from '../_services';

export const reActions = {
    submit,
    createUser
};

function submit(userId, name, mIsId, mortId) {
    return dispatch => {
        dispatch(request());

        reService.appraise(userId, name, mIsId, mortId)
            .then(
                id => dispatch(success(id)),
                error => dispatch(failure(error))
            );
    };

    function request() { return { type: reConstants.APPRAISAL_REQUEST } }
    function success(id) { return { type: reConstants.APPRAISAL_SUCCESS, id } }
    function failure(error) { return { type: reConstants.APPRAISAL_FAILURE, error } }
}

function createUser(username, password) {
    return dispatch => {
        dispatch(request());

        reService.createUser(username, password)
            .then(
                response => dispatch(success(response)),
                error => dispatch(failure(error))
            );
    };

    function request() { return { type: userConstants.CREATE_USER_REQUEST } }
    function success(id) { return { type: userConstants.CREATE_USER_SUCCESS, id } }
    function failure(error) { return { type: userConstants.CREATE_USER_FAILURE, error } }
}