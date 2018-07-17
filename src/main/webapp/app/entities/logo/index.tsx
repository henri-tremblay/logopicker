import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Logo from './logo';
import LogoDetail from './logo-detail';
import LogoUpdate from './logo-update';
import LogoDeleteDialog from './logo-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LogoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LogoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LogoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Logo} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LogoDeleteDialog} />
  </>
);

export default Routes;
