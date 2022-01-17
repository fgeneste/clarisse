import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAppartenance } from '@/shared/model/appartenance.model';
import AppartenanceService from './appartenance.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AppartenanceDetails extends Vue {
  @Inject('appartenanceService') private appartenanceService: () => AppartenanceService;
  @Inject('alertService') private alertService: () => AlertService;

  public appartenance: IAppartenance = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.appartenanceId) {
        vm.retrieveAppartenance(to.params.appartenanceId);
      }
    });
  }

  public retrieveAppartenance(appartenanceId) {
    this.appartenanceService()
      .find(appartenanceId)
      .then(res => {
        this.appartenance = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
