//
//  ScanQRCodeVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 5/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//


import AVFoundation
import UIKit

class ScanQRCodeVC: BaseVC, AVCaptureMetadataOutputObjectsDelegate {
    
    var homeVC : HomeVC?
    
    var captureSession: AVCaptureSession!
    var previewLayer: AVCaptureVideoPreviewLayer!
    
    @IBOutlet weak var vv : UIView!
    @IBOutlet weak var lbl : ActiveLabel!
    @IBOutlet weak var pinLbl : UILabel!
    @IBOutlet weak var pinRes : UILabel!
    @IBOutlet weak var vvRes : UIView!

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Scan QRcode".localized
        
        
        pinLbl.font = Theme.Font.Calibri.withSize(16)
        pinRes.font = Theme.Font.Calibri.withSize(20)
        
        self.lbl.font = Theme.Font.Calibri.withSize(16)
        self.lbl.enabledTypes = [.url]
        lbl.URLColor = Theme.Color.White
        self.lbl.handleURLTap {res  in
            if UIApplication.shared.canOpenURL(res){
                UIApplication.shared.openURL(res)
            }
        }

        
        view.backgroundColor = UIColor.black
        captureSession = AVCaptureSession()
        
        guard let videoCaptureDevice = AVCaptureDevice.default(for: .video) else { return }
        let videoInput: AVCaptureDeviceInput
        
        do {
            videoInput = try AVCaptureDeviceInput(device: videoCaptureDevice)
        } catch {
            return
        }
        
        if (captureSession.canAddInput(videoInput)) {
            captureSession.addInput(videoInput)
        } else {
            failed()
            return
        }
        
        let metadataOutput = AVCaptureMetadataOutput()
        
        if (captureSession.canAddOutput(metadataOutput)) {
            captureSession.addOutput(metadataOutput)
            
            metadataOutput.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
            metadataOutput.metadataObjectTypes = [.qr]
        } else {
            failed()
            return
        }
        
        previewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
        previewLayer.frame = vv.layer.bounds
        previewLayer.frame.size.width = self.view.frame.width - 20
        previewLayer.frame.size.height = self.view.frame.width - 20
        previewLayer.videoGravity = .resizeAspectFill
        vv.layer.addSublayer(previewLayer)
        
        
        captureSession.startRunning()
    }
    
    func failed() {
        let ac = UIAlertController(title: "Scanning not supported", message: "Your device does not support scanning a code from an item. Please use a device with a camera.", preferredStyle: .alert)
        ac.addAction(UIAlertAction(title: "OK", style: .default))
        present(ac, animated: true)
        captureSession = nil
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        if (captureSession?.isRunning == false) {
            captureSession.startRunning()
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        if (captureSession?.isRunning == true) {
            captureSession.stopRunning()
        }
    }
    
    func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects: [AVMetadataObject], from connection: AVCaptureConnection) {
        captureSession.stopRunning()
        
        if let metadataObject = metadataObjects.first {
            guard let readableObject = metadataObject as? AVMetadataMachineReadableCodeObject else { return }
            guard let stringValue = readableObject.stringValue else { return }
            AudioServicesPlaySystemSound(SystemSoundID(kSystemSoundID_Vibrate))
            found(code: stringValue)
        }
        
        dismiss(animated: true)
    }
    
    func found(code: String) {
        
        self.showLoading()
        Communication.shared.QR_code_scan(gen_code: code) { (res) in
            self.hideLoading()
            
            
            self.pinRes.text = res
            self.vvRes.isHidden = false
            
//            let alert = UIAlertController.init(title: "PIN NUMBER", message: res, preferredStyle: .alert)
//            
//            alert.addAction(UIAlertAction.init(title: "OK".localized, style: .default, handler: nil))
//            self.present(alert, animated: true, completion: nil)
        }
        
        
    }
    
//    override var prefersStatusBarHidden: Bool {
//        return true
//    }
    
    //    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
    //        return .portrait
    //    }
}
