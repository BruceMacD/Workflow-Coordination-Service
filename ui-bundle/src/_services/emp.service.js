import { authHeader } from '../_helpers';
import { jwtConstants, serverConstants } from '../_constants';

export const empService = {
    patch,
    apply,
    createUser
};

function patch(id, name, salary, employmentStartDate) {

    // if submitted assume has not yet applied
    const applied = false;
    
    let user = JSON.parse(localStorage.getItem(jwtConstants.EMP_TOKEN));

    const requestOptions = {
        method: 'PATCH',
        headers: {
            'x-auth-token': user.token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id, name, salary, employmentStartDate, applied })
    };
    
    return fetch(serverConstants.EMP_SERVER_ROOT_ENDPOINT + '/' + id, requestOptions)
        .then(response => {
            if (!response.ok) {
                return Promise.reject(response.statusText);
            }
            return response.json();
        }).then(responseBody => {
            return responseBody;
        });
}

function apply(id, mortId){
    
    let user = JSON.parse(localStorage.getItem(jwtConstants.EMP_TOKEN));

    const requestOptions = {
        method: 'POST',
        headers: {
            'x-auth-token': user.token
        }
    };

    const endpoint = serverConstants.EMP_SERVER_ROOT_ENDPOINT + '/' + id + '/application/' + mortId; 
    
    return fetch(endpoint, requestOptions)
        .then(response => {
            if (!response.ok) {
                return Promise.reject(response.statusText);
            }
            return response.json();
        }).then(responseBody => {
            return responseBody;
        });
}

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
