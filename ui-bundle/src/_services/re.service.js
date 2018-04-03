import { authHeader } from '../_helpers';
import { jwtConstants, serverConstants } from '../_constants';

export const reService = {
    createUser,
    appraise
};

function createUser(id, password) {

    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id, password })
    };

    return fetch(serverConstants.RE_SERVER_CREATE_USER_ENDPOINT, requestOptions)
        .then(response => {
            if (!response.ok) {
                return Promise.reject(response.statusText);
            }
            return response.json();
        }).then(responseBody => {
            return responseBody;
        });
}

function appraise(userId, name, mortgageInsuranceId, mortgageId) {
    
    let user = JSON.parse(localStorage.getItem(jwtConstants.RE_TOKEN));

    const requestOptions = {
        method: 'POST',
        headers: {
            'x-auth-token': user.token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, mortgageInsuranceId, mortgageId })
    };

    const endpoint = serverConstants.RE_APPRAISAL_ENDPOINT + '/' + userId + '/appraisal'; 
    
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
