import { authHeader } from '../_helpers';
import { jwtConstants, serverConstants } from '../_constants';

export const mbrService = {
    create,
    createUser,
    getStatus
};

function create(id, name, mortgageValue, mortgageInsuranceId) {

    const mortgageVal = Number.parseInt(mortgageValue);
    
    let user = JSON.parse(localStorage.getItem(jwtConstants.MBR_TOKEN));

    const requestOptions = {
        method: 'POST',
        headers: {
            'x-auth-token': user.token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, mortgageVal, mortgageInsuranceId })
    };
    
    return fetch(serverConstants.MBR_SERVER_CREATE_ENDPOINT + '/' + id + '/application', requestOptions)
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

    return fetch(serverConstants.MBR_SERVER_CREATE_USER_ENDPOINT, requestOptions)
        .then(response => {
            if (!response.ok) {
                return Promise.reject(response.statusText);
            }
            return response.json();
        }).then(responseBody => {
            return responseBody;
        });
}

function getStatus(id) {

    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    const endpoint = serverConstants.MBR_SERVER_GET_STATUS_ENDPOINT + '/' + id;

    return fetch( endpoint, requestOptions)
        .then(response => {
            if (!response.ok) {
                return Promise.reject(response.statusText);
            }
            return response.json();
        }).then(responseBody => {
            return responseBody;
        });
}