import { Component, Vue, Inject } from 'vue-property-decorator';

import { INoms } from '@/shared/model/noms.model';
import NomsService from './noms.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class NomsDetails extends Vue {
  @Inject('nomsService') private nomsService: () => NomsService;
  @Inject('alertService') private alertService: () => AlertService;

  public noms: INoms = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.nomsId) {
        vm.retrieveNoms(to.params.nomsId);
      }
    });
  }

  public retrieveNoms(nomsId) {
    this.nomsService()
      .find(nomsId)
      .then(res => {
        this.noms = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
