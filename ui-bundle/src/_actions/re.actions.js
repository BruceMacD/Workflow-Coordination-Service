import { reConstants, userConstants } from '../_constants';
import { reService } from '../_services';

export const reActions = {
    submit,
    createUser
};

//TODO
function submit(id) {
    // return dispatch => {
    //     dispatch(request());

    //     mbrService.create(id, name, mortgageVal, mortgageInsuranceId)
    //         .then(
    //             id => dispatch(success(id)),
    //             error => dispatch(failure(error))
    //         );
    // };

    // function request() { return { type: mbrConstants.CREATE_REQUEST } }
    // function success(id) { return { type: mbrConstants.CREATE_SUCCESS, id } }
    // function failure(error) { return { type: mbrConstants.CREATE_FAILURE, error } }
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