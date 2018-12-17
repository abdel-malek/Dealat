//
//  MyFavoritesVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import XLPagerTabStrip

class MyFavoritesVC: BaseVC,UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout,IndicatorInfoProvider {
    
    @IBOutlet weak var collectionView : UICollectionView!
    var ads = [AD]()
    var itemInfo = IndicatorInfo(title: "Favorites".localized)
    
    var type = 0 // 0 for favorites, 1 for saved searches
    var bookmark : UserBookmark!
    
    var pageNumber = 1
    var isDataLoading : Bool = false
    var isAllDataFetched : Bool = false
    let indicater : UIActivityIndicatorView = UIActivityIndicatorView(style: .gray)

    
    override func viewDidLoad() {
        super.viewDidLoad()

        getData()
        
        Provider.setScreenName("My Favorites")
    }
    
    override func setupViews() {
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
        self.collectionView.addSubview(ref)
    }
    
    override func getRefreshing() {
        self.isDataLoading = false
        self.isAllDataFetched = false
        self.pageNumber = 1
        
        getRefreshing2()
    }
    
    func getRefreshing2(){
        if type == 0{
            Communication.shared.get_my_favorites(page_num: self.pageNumber) { (res) in
                self.hideLoading()
                self.onDataRecived(res)
            }
        }else{
//            self.title = self.bookmark.getName()
            Communication.shared.get_bookmark_search(page_num : self.pageNumber,user_bookmark_id: bookmark.user_bookmark_id.intValue, callback: { (commercials, ads) in
                self.hideLoading()
                self.onDataRecived(ads)
            })
        }
    }
    
    func onDataRecived(_ res : [AD]){
        self.hideLoading()
        self.collectionView.isHidden = false
        isDataLoading = false
        
        if self.pageNumber == 1{
            self.ads = res
        }else{
            self.ads = self.ads + res
        }
        
        if (res.count < Provider.PAGE_SIZE)
        {
            isAllDataFetched = true
        }
        
        self.collectionView.reloadData()
    }
    
    func loadItems()
    {
        
        if(isDataLoading || isAllDataFetched)
        {
            return
        }
        
        isDataLoading = true
        pageNumber = pageNumber + 1
        
        indicater.startAnimating()
        indicater.hidesWhenStopped = true
        indicater.frame.size.height = 40
        
        
        getRefreshing2()
    }


    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
            return ads.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! AdCell
            cell.ad = self.ads[indexPath.row]
        
        //check if it is the last item at the list
        if(indexPath.row == self.ads.count - 1)
        {
            loadItems()
        }
        
            return cell
    }
    
    
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdDetailsBaseVC") as! AdDetailsBaseVC
        vc.tamplateId = self.ads[indexPath.row].tamplate_id.intValue
        vc.ad = self.ads[indexPath.row]
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        return CGSize.init(width: collectionView.frame.width, height: UIScreen.main.bounds.width / 2)
    }
    
    
    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return self.itemInfo
    }

}
