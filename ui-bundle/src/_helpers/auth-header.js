export function authHeader(token) {
    // return authorization header with jwt token
    let user = JSON.parse(localStorage.getItem(token));

    if (user && user.token) {
        return { 'x-auth-token': 'Bearer ' + user.token };
    } else {
        return {};
    }
}