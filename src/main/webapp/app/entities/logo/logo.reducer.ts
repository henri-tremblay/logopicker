import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILogo, defaultValue } from 'app/shared/model/logo.model';

export const ACTION_TYPES = {
  FETCH_LOGO_LIST: 'logo/FETCH_LOGO_LIST',
  FETCH_LOGO: 'logo/FETCH_LOGO',
  FETCH_CURRENT_LOGO: 'logo/FETCH_CURRENT_LOGO',
  CREATE_LOGO: 'logo/CREATE_LOGO',
  UPDATE_LOGO: 'logo/UPDATE_LOGO',
  DELETE_LOGO: 'logo/DELETE_LOGO',
  RESET: 'logo/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILogo>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type LogoState = Readonly<typeof initialState>;

// Reducer

export default (state: LogoState = initialState, action): LogoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LOGO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LOGO):
    case REQUEST(ACTION_TYPES.FETCH_CURRENT_LOGO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LOGO):
    case REQUEST(ACTION_TYPES.UPDATE_LOGO):
    case REQUEST(ACTION_TYPES.DELETE_LOGO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LOGO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LOGO):
    case FAILURE(ACTION_TYPES.FETCH_CURRENT_LOGO):
    case FAILURE(ACTION_TYPES.CREATE_LOGO):
    case FAILURE(ACTION_TYPES.UPDATE_LOGO):
    case FAILURE(ACTION_TYPES.DELETE_LOGO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOGO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOGO):
    case SUCCESS(ACTION_TYPES.FETCH_CURRENT_LOGO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LOGO):
    case SUCCESS(ACTION_TYPES.UPDATE_LOGO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LOGO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/logos';

// Actions

export const getEntities: ICrudGetAllAction<ILogo> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LOGO_LIST,
  payload: axios.get<ILogo>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ILogo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LOGO,
    payload: axios.get<ILogo>(requestUrl)
  };
};

export const getCurrentLogo: ICrudGetAction<ILogo> = () => {
  const requestUrl = `${apiUrl}/current`;
  return {
    type: ACTION_TYPES.FETCH_CURRENT_LOGO,
    payload: axios.get<ILogo>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILogo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LOGO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILogo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LOGO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILogo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LOGO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
