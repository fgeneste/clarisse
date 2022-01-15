/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PersonneComponent from '@/entities/personne/personne.vue';
import PersonneClass from '@/entities/personne/personne.component';
import PersonneService from '@/entities/personne/personne.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Personne Management Component', () => {
    let wrapper: Wrapper<PersonneClass>;
    let comp: PersonneClass;
    let personneServiceStub: SinonStubbedInstance<PersonneService>;

    beforeEach(() => {
      personneServiceStub = sinon.createStubInstance<PersonneService>(PersonneService);
      personneServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PersonneClass>(PersonneComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          personneService: () => personneServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      personneServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPersonnes();
      await comp.$nextTick();

      // THEN
      expect(personneServiceStub.retrieve.called).toBeTruthy();
      expect(comp.personnes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      personneServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(personneServiceStub.retrieve.callCount).toEqual(1);

      comp.removePersonne();
      await comp.$nextTick();

      // THEN
      expect(personneServiceStub.delete.called).toBeTruthy();
      expect(personneServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
