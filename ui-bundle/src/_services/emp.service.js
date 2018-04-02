import { authHeader } from '../_helpers';
import { jwtConstants, serverConstants } from '../_constants';

export const empService = {
    createUser
};

function createUser(id, password) {

    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id, password })
    };

    return fetch(serverConstants.EMP_SERVER_CREATE_USER_ENDPOINT, requestOptions)
        .then(response => {
            if (!response.ok) {
                return Promise.reject(response.statusText);
            }
            return response.json();
        }).then(responseBody => {
            return responseBody;
        });
}
