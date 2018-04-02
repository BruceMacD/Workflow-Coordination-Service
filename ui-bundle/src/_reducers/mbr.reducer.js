import { mbrConstants } from '../_constants';

const initialState = { application:{ } };

export function mbr(state = initialState, action) {
  switch (action.type) {
    case mbrConstants.GET_STATUS_SUCCESS:
      return {
        application: action.id
      };
    case mbrConstants.CREATE_SUCCESS:
      return {
        application: action.id
      };
    default:
      return state
  }
}