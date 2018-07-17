import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './logo.reducer';
import { ILogo } from 'app/shared/model/logo.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface ILogoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface ILogoUpdateState {
  isNew: boolean;
}

export class LogoUpdate extends React.Component<ILogoUpdateProps, ILogoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { logoEntity } = this.props;
      const entity = {
        ...logoEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/logo');
  };

  render() {
    const { logoEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="logopickerApp.logo.home.createOrEditLabel">Create or edit a Logo</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : logoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="logo-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="cloudLabel">Cloud</Label>
                  <AvInput
                    id="logo-cloud"
                    type="select"
                    className="form-control"
                    name="cloud"
                    value={(!isNew && logoEntity.cloud) || 'UNKNOWN'}
                  >
                    <option value="UNKNOWN">UNKNOWN</option>
                    <option value="LOCALHOST">LOCALHOST</option>
                    <option value="HEROKU">HEROKU</option>
                    <option value="ORACLE">ORACLE</option>
                    <option value="AWS">AWS</option>
                    <option value="AZURE">AZURE</option>
                    <option value="CLOUD_FOUNDRY">CLOUD_FOUNDRY</option>
                    <option value="GOOGLE">GOOGLE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="logo-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="urlLabel" for="url">
                    Url
                  </Label>
                  <AvField id="logo-url" type="text" name="url" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/logo" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  logoEntity: storeState.logo.entity,
  loading: storeState.logo.loading,
  updating: storeState.logo.updating
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LogoUpdate);
