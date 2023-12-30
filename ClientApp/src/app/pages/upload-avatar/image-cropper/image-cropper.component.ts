import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

// import Cropper from 'cropperjs';

@Component({
  selector: 'vex-image-cropper',
  templateUrl: './image-cropper.component.html',
  styleUrls: ['./image-cropper.component.scss']
})
export class ImageCropperComponent implements OnInit {
  sanitizedUrl!: SafeUrl;
  // cropper!: Cropper;

  constructor(
    public dialogRef: MatDialogRef<ImageCropperComponent>,
    @Inject(MAT_DIALOG_DATA) public image: string,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.sanitizedUrl = this.sanitizer.bypassSecurityTrustUrl(this.image);
  }

  ngAfterViewInit() {
    this.initCropper();
  }

  initCropper() {

    const image = document.getElementById('image') as HTMLImageElement;
    // this.cropper = new Cropper(image, {
    //   aspectRatio: 1,
    //   viewMode: 1,
    //   guides: false,

    // });
  }

  getRoundedCanvas(sourceCanvas: any) {
    var canvas = document.createElement('canvas');
    var context: any = canvas.getContext('2d');
    var width = sourceCanvas.width;
    var height = sourceCanvas.height;

    canvas.width = width;
    canvas.height = height;
    context.imageSmoothingEnabled = true;
    context.drawImage(sourceCanvas, 0, 0, width, height);
    context.globalCompositeOperation = 'destination-in';
    context.beginPath();
    context.arc(
      width / 2,
      height / 2,
      Math.min(width, height) / 2,
      0,
      2 * Math.PI,
      true
    );
    context.fill();
    return canvas;
  }

  crop() {
    // const croppedCanvas = this.cropper.getCroppedCanvas();
    // const roundedCanvas = this.getRoundedCanvas(croppedCanvas);

    // let roundedImage = document.createElement('img');

    // if (roundedImage) {
    //   this.dialogRef.close(roundedCanvas.toDataURL());
    // } else {
    //   return this.dialogRef.close(null);
    // }
  }

  reset(){
    // this.cropper.clear();
    // this.cropper.crop();
  }
}
