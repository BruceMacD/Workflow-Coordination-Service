import { userConstants } from '../_constants';

const initialState = { id: '', error: '' };

export function users(state = initialState, action) {
  switch (action.type) {
    case userConstants.CREATE_USER_SUCCESS:
      return {
        id: action.id.id
      };
    case userConstants.CREATE_USER_FAILURE:
      return { 
        error: action.error
      };
    default:
      return state
  }
}