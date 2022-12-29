import { ToastSeverity } from 'primevue/api';
import { vueApp } from '../main';

const toastConfif = {
  life: 3000,
  closable: true,
};

function success(summary: string, detail: string): void {
  vueApp.config.globalProperties.$toast.add({
    severity: ToastSeverity.SUCCESS,
    summary: summary,
    detail: detail,
    ...toastConfif,
  });
}

function error(summary: string, ...details: string[]): void {
  for (let detail of details) {
    console.log(detail)
    vueApp.config.globalProperties.$toast.add({
      severity: ToastSeverity.ERROR,
      summary: summary,
      detail: detail,
      ...toastConfif,
    });
  }
}

function warn(summary: string, detail: string): void {
  vueApp.config.globalProperties.$toast.add({
    severity: ToastSeverity.WARN,
    summary: summary,
    detail: detail,
    ...toastConfif,
  });
}

const improvedToast = {
  success: success,
  error: error,
  warn: warn,
};

export default improvedToast;
