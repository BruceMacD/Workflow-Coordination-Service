import { mbrConstants } from '../_constants';
import { mbrService } from '../_services';

export const mbrActions = {
    submit
};

function submit(name, mortgageValue, houseId) {
    return dispatch => {
        dispatch(request());

        mbrService.create(name, mortgageValue, houseId)
            .then(
                id => dispatch(success(id)),
                error => dispatch(failure(error))
            );
    };

    function request() { return { type: mbrConstants.CREATE_REQUEST } }
    function success(id) { return { type: mbrConstants.CREATE_SUCCESS, id } }
    function failure(error) { return { type: mbrConstants.CREATE_FAILURE, error } }
}