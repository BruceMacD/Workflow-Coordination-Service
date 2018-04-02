import { combineReducers } from 'redux';

import { mbrAuthentication } from './mbr.authentication.reducer';
import { empAuthentication } from './emp.authentication.reducer';
import { reAuthentication } from './re.authentication.reducer';
import { mbr } from './mbr.reducer';
import { users } from './users.reducer';
import { alert } from './alert.reducer';

const rootReducer = combineReducers({
  mbrAuthentication,
  empAuthentication,
  reAuthentication,
  mbr,
  users,
  alert
});

export default rootReducer;