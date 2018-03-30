import { mbrConstants } from '../_constants';

const initialState = { status: { emp: 'pending', ins: 'pending' } };

export function mbr(state = initialState, action) {
  switch (action.type) {
    case mbrConstants.GET_STATUS_SUCCESS:
      return {
        status: action.status
      };
    default:
      return state
  }
}