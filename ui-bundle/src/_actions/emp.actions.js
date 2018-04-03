import { empConstants, userConstants } from '../_constants';
import { empService } from '../_services';

export const empActions = {
    submit,
    apply,
    createUser
};

function submit(id, name, salary, startDate) {
    return dispatch => {
        dispatch(request());

        empService.patch(id, name, salary, startDate)
            .then(
                id => dispatch(success(id)),
                error => dispatch(failure(error))
            );
    };

    function request() { return { type: empConstants.PATCH_REQUEST } }
    function success(id) { return { type: empConstants.PATCH_SUCCESS, id } }
    function failure(error) { return { type: empConstants.PATCH_FAILURE, error } }
}

function apply(id, mortId){
    return dispatch => {
        dispatch(request());

        empService.apply(id, mortId)
            .then(
                id => dispatch(success(id)),
                error => dispatch(failure(error))
            );
    };

    function request() { return { type: empConstants.APPLY_REQUEST } }
    function success(id) { return { type: empConstants.APPLY_SUCCESS, id } }
    function failure(error) { return { type: empConstants.APPLY_FAILURE, error } }
}

function createUser(username, password) {
    return dispatch => {
        dispatch(request());

        empService.createUser(username, password)
            .then(
                response => dispatch(success(response)),
                error => dispatch(failure(error))
            );
    };

    function request() { return { type: userConstants.CREATE_USER_REQUEST } }
    function success(id) { return { type: userConstants.CREATE_USER_SUCCESS, id } }
    function failure(error) { return { type: userConstants.CREATE_USER_FAILURE, error } }
}