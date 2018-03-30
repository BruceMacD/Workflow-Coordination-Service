import { combineReducers } from 'redux';

import { authentication } from './authentication.reducer';
import { mbr } from './mbr.reducer';
import { users } from './users.reducer';
import { alert } from './alert.reducer';

const rootReducer = combineReducers({
  authentication,
  mbr,
  users,
  alert
});

export default rootReducer;