import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPersonne } from '@/shared/model/personne.model';
import PersonneService from './personne.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PersonneDetails extends Vue {
  @Inject('personneService') private personneService: () => PersonneService;
  @Inject('alertService') private alertService: () => AlertService;

  public personne: IPersonne = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personneId) {
        vm.retrievePersonne(to.params.personneId);
      }
    });
  }

  public retrievePersonne(personneId) {
    this.personneService()
      .find(personneId)
      .then(res => {
        this.personne = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
