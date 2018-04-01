import { userConstants } from '../_constants';
import { userService } from '../_services';
import { alertActions } from './';
import { history } from '../_helpers';

export const userActions = {
    login,
    logout
};

function login(portal, id, password) {
    return dispatch => {
        dispatch(request({ id }));

        userService.login(portal, id, password)
            .then(
                user => { 
                    dispatch(success({id, user}));
                    // redirect based on portal login used
                    history.push('/' + portal);
                },
                error => {
                    dispatch(failure(error));
                    dispatch(alertActions.error(error));
                }
            );
    };

    function request(user) { return { type: userConstants.LOGIN_REQUEST, user } }
    function success(user) { return { type: userConstants.LOGIN_SUCCESS, user } }
    function failure(error) { return { type: userConstants.LOGIN_FAILURE, error } }
}

function logout(portal) {
    userService.logout(portal);
    return { type: userConstants.LOGOUT };
}